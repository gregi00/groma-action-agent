import { defineConfig, devices } from '@playwright/test';

const port = process.env.E2E_PORT ?? '8081';

export default defineConfig({
  testDir: './e2e',
  fullyParallel: true,
  reporter: process.env.CI
    ? [
        ['list'],
        ['github'],
        ['html', { outputFolder: 'playwright-report', open: 'never' }],
        ['json', { outputFile: 'test-results/results.json' }],
      ]
    : [['list'], ['html', { outputFolder: 'playwright-report', open: 'never' }]],
  use: {
    baseURL: `http://127.0.0.1:${port}`,
    trace: 'retain-on-failure',
    ...devices['Desktop Chrome'],
  },
  webServer: {
    command: 'mvn -B -pl wortsuche-web -am spring-boot:run',
    url: `http://127.0.0.1:${port}`,
    reuseExistingServer: !process.env.CI,
    timeout: 120_000,
    env: {
      SPRING_DATASOURCE_URL: 'jdbc:h2:mem:wortsuche;DB_CLOSE_DELAY=-1',
      SPRING_JPA_HIBERNATE_DDL_AUTO: 'create-drop',
      SERVER_PORT: port,
    },
  },
});
