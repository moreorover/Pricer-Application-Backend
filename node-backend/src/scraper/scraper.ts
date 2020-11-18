import pupeteer from 'puppeteer'

export type Scraper = {
  browser: pupeteer.Browser
  page: pupeteer.Page
}
