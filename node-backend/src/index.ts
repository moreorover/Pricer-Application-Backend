import { createConnection } from 'typeorm'
import express from 'express'
import path from 'path'
import { Store } from './entities/Store'
import scrapeUrl from './scraper/CreationWatches'

const main = async () => {
  await createConnection({
    type: 'mysql',
    host: '',
    database: '',
    username: '',
    password: '',
    logging: true,
    synchronize: true,
    entities: [path.join(__dirname, './entities/*')],
  })

  let store: Store | undefined

  store = await Store.findOne(1)

  console.log(store)
  console.log('url', store?.urls[0].url)

  const urlToScrape = store?.urls[0].url

  scrapeUrl(urlToScrape!)

  const app = express()

  app.listen(4000, () => {
    console.log('server started on localhost:4000')
  })
}

main()
