import { expect, test } from '@playwright/test';

const uniqueWord = () => `Playwright-${crypto.randomUUID()}`;

test('zeigt die Wortsuche und das Formular zum Hinzufügen', async ({ page }) => {
  await page.goto('/');

  await expect(page).toHaveTitle('Wortsuche');
  await expect(page.getByRole('heading', { name: 'Ein Wort nachschlagen' })).toBeVisible();
  await expect(page.getByRole('heading', { name: 'Neuen Eintrag hinzufügen' })).toBeVisible();
});

test('speichert einen Eintrag und findet ihn ohne Beachtung der Großschreibung', async ({ page }) => {
  const word = uniqueWord();
  const definition = `E2E-Testdefinition ${word}`;

  await page.goto('/');
  await page.getByLabel('Wort', { exact: true }).fill(word);
  await page.getByLabel('Zugehöriger Text').fill(definition);
  await page.getByRole('button', { name: 'Eintrag speichern' }).click();
  await expect(page.getByText('Der Eintrag wurde gespeichert.')).toBeVisible();

  await page.getByLabel('Suchwort').fill(word.toUpperCase());
  await page.getByRole('button', { name: 'Suchen' }).click();
  await expect(page.locator('.result').getByText(definition)).toBeVisible();
});

test('ordnet Einträge anhand der Erklärungstexte ein und klappt Kategorien auf und zu', async ({ page }) => {
  const naturalWord = uniqueWord();
  const generalWord = uniqueWord();

  await page.goto('/');
  await page.getByLabel('Wort', { exact: true }).fill(naturalWord);
  await page.getByLabel('Zugehöriger Text').fill('Ein großer Baum wächst im Wald.');
  await page.getByRole('button', { name: 'Eintrag speichern' }).click();

  await page.getByLabel('Wort', { exact: true }).fill(generalWord);
  await page.getByLabel('Zugehöriger Text').fill('Eine abstrakte Idee ohne thematische Hinweise.');
  await page.getByRole('button', { name: 'Eintrag speichern' }).click();

  const natureGroup = page.locator('.entry-category').filter({
    has: page.getByText('Natur & Umwelt', { exact: true }),
  });
  const generalGroup = page.locator('.entry-category').filter({
    has: page.getByText('Allgemein', { exact: true }),
  });
  const naturalEntry = natureGroup.getByRole('article').filter({ hasText: naturalWord });
  const generalEntry = generalGroup.getByRole('article').filter({ hasText: generalWord });

  await expect(natureGroup).toBeVisible();
  await expect(naturalEntry).toBeVisible();
  await expect(generalEntry).toBeVisible();

  await natureGroup.locator('summary').click();
  await expect(naturalEntry).toBeHidden();
  await natureGroup.locator('summary').click();
  await expect(naturalEntry).toBeVisible();
});

test('verhindert doppelte Einträge', async ({ page }) => {
  const word = uniqueWord();
  const entry = page.getByRole('article').filter({
    has: page.getByRole('heading', { name: word, exact: true }),
  });

  await page.goto('/');
  await page.getByLabel('Wort', { exact: true }).fill(word);
  await page.getByLabel('Zugehöriger Text').fill('Erster Text');
  await page.getByRole('button', { name: 'Eintrag speichern' }).click();
  await expect(page.getByText('Der Eintrag wurde gespeichert.')).toBeVisible();

  await page.getByLabel('Wort', { exact: true }).fill(word);
  await page.getByLabel('Zugehöriger Text').fill('Zweiter Text');
  await page.getByRole('button', { name: 'Eintrag speichern' }).click();
  await expect(page.getByText(`Für „${word}“ gibt es bereits einen Eintrag.`)).toBeVisible();
  await expect(entry).toHaveCount(1);

  await entry.getByRole('button', { name: `Eintrag ${word} löschen` }).click();
});

test('löscht einen gespeicherten Eintrag', async ({ page }) => {
  const word = uniqueWord();
  const entry = page.getByRole('article').filter({
    has: page.getByRole('heading', { name: word, exact: true }),
  });

  await page.goto('/');
  await page.getByLabel('Wort', { exact: true }).fill(word);
  await page.getByLabel('Zugehöriger Text').fill('Wird wieder gelöscht');
  await page.getByRole('button', { name: 'Eintrag speichern' }).click();
  await expect(entry).toBeVisible();

  await entry.getByRole('button', { name: `Eintrag ${word} löschen` }).click();
  await expect(page.getByText('Der Eintrag wurde gelöscht.')).toBeVisible();
  await expect(entry).toHaveCount(0);
});
