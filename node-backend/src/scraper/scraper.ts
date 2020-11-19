import pupeteer from 'puppeteer'

export type Scraper = {
  browser: pupeteer.Browser
  page: pupeteer.Page
}

export type AdSelectors = {
  ads: string
  title: string
  url: string
  upc: string
  price: string
  img: string
}
