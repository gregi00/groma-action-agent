const categories = [
  {
    name: 'Gefühle & Persönlichkeit',
    keywords: [
      'gefühl', 'emotion', 'angst', 'freude', 'liebe', 'mut', 'trauer', 'wut',
      'hoffnung', 'zuversicht', 'stolz', 'charakter', 'eigenschaft', 'glücklich',
      'traurig', 'freundlich', 'selbstvertrauen',
    ],
  },
  {
    name: 'Natur & Umwelt',
    keywords: [
      'natur', 'pflanz', 'tier', 'baum', 'blume', 'wald', 'wasser', 'luft',
      'erde', 'wetter', 'klima', 'umwelt', 'sonne', 'meer', 'vogel', 'fisch',
      'berg', 'fluss', 'landschaft',
    ],
  },
  {
    name: 'Menschen & Gesellschaft',
    keywords: [
      'mensch', 'person', 'familie', 'freund', 'gesellschaft', 'gemeinschaft',
      'beruf', 'arbeit', 'beziehung', 'kind', 'nachbar',
      'politik', 'recht', 'bildung',
    ],
  },
  {
    name: 'Wissenschaft & Technik',
    keywords: [
      'wissenschaft', 'technik', 'computer', 'digital', 'internet', 'energie',
      'physik', 'chemie', 'biologie', 'forschung', 'maschine', 'algorithm',
      'daten', 'software', 'elektrizität',
    ],
  },
  {
    name: 'Kunst & Kultur',
    keywords: [
      'kunst', 'musik', 'literatur', 'sprache', 'buch', 'gedicht', 'theater',
      'film', 'geschichte', 'religion', 'malerei', 'kultur', 'tanz', 'design',
    ],
  },
  {
    name: 'Alltag & Gegenstände',
    keywords: [
      'gegenstand', 'werkzeug', 'haushalt', 'essen', 'trinken', 'kleidung',
      'haus', 'fahrzeug', 'küche', 'möbel', 'gerät', 'produkt', 'lebensmittel',
    ],
  },
];

function normalize(text) {
  return text
    .toLocaleLowerCase('de')
    .normalize('NFD')
    .replace(/[\u0300-\u036f]/g, '');
}

function categoryFor(text) {
  const words = normalize(text).match(/[a-z0-9]+/g) ?? [];
  return categories.find(({ keywords }) =>
    keywords.some((keyword) => {
      const normalizedKeyword = normalize(keyword);
      return words.some((word) =>
        word === normalizedKeyword ||
        (normalizedKeyword.length >= 5 && word.startsWith(normalizedKeyword)),
      );
    }),
  )?.name ?? 'Allgemein';
}

function groupEntries() {
  const list = document.querySelector('#entry-list');
  if (!list) return;

  const entries = [...list.querySelectorAll(':scope > .entry')];
  if (entries.length === 0) return;

  const grouped = new Map();
  for (const entry of entries) {
    const definition = entry.querySelector('p')?.textContent
      ?? entry.querySelector('textarea[name="text"]')?.value
      ?? '';
    const category = categoryFor(definition);
    if (!grouped.has(category)) grouped.set(category, []);
    grouped.get(category).push(entry);
  }

  const categoryOrder = [...categories.map(({ name }) => name), 'Allgemein'];
  for (const category of categoryOrder) {
    const categoryEntries = grouped.get(category);
    if (!categoryEntries) continue;

    const group = document.createElement('details');
    group.className = 'entry-category';
    group.open = true;

    const summary = document.createElement('summary');
    const name = document.createElement('span');
    name.textContent = category;
    const count = document.createElement('span');
    count.className = 'category-count';
    count.textContent = `${categoryEntries.length} ${
      categoryEntries.length === 1 ? 'Eintrag' : 'Einträge'
    }`;
    summary.append(name, count);

    const groupEntries = document.createElement('div');
    groupEntries.className = 'category-entries';
    groupEntries.append(...categoryEntries);
    group.append(summary, groupEntries);
    list.append(group);
  }
}

groupEntries();
