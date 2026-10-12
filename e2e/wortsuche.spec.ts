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
