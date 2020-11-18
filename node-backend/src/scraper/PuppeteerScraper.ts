import pupeteer from 'puppeteer'
import { Scraper } from './scraper'

export class PuppeteerScraper implements Scraper {
  browser: pupeteer.Browser
  page: pupeteer.Page

  async init() {
    this.browser = await pupeteer.launch({
      headless: true,
    })
    this.page = await this.browser.newPage()
    this.page.setJavaScriptEnabled(true)
  }

  async fetchUrl(url: string) {
    await this.page.goto(url, { waitUntil: 'networkidle0' })
  }

  async closeBrowser() {
    await this.browser.close()
  }
}
