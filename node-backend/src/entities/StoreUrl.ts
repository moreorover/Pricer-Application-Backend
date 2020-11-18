import {
  Entity,
  Column,
  PrimaryGeneratedColumn,
  CreateDateColumn,
  UpdateDateColumn,
  BaseEntity,
  JoinColumn,
  OneToOne,
  ManyToOne,
} from 'typeorm'
import { Status } from './Status'
import { Store } from './Store'

@Entity()
export class Url extends BaseEntity {
  @PrimaryGeneratedColumn()
  id!: number

  @Column({ unique: true })
  url: string

  @Column()
  storeId: number

  @ManyToOne(() => Store, store => store.urls)
  store: Store

  @Column()
  statusId: number

  @OneToOne(() => Status)
  @JoinColumn()
  Status: Status

  @CreateDateColumn()
  createdAt: Date

  @UpdateDateColumn()
  updatedAt: Date
}
