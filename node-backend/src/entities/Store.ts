import { Url } from './StoreUrl'
import {
  Entity,
  Column,
  PrimaryGeneratedColumn,
  CreateDateColumn,
  UpdateDateColumn,
  BaseEntity,
  OneToMany,
} from 'typeorm'

@Entity()
export class Store extends BaseEntity {
  @PrimaryGeneratedColumn()
  id!: number

  @Column({ unique: true })
  name: string

  @Column({ unique: true })
  homeUrl: string

  @Column({ unique: true })
  logoUrl: string

  @OneToMany(() => Url, url => url.store, { eager: true })
  urls: Url[]

  @CreateDateColumn()
  createdAt: Date

  @UpdateDateColumn()
  updatedAt: Date
}
