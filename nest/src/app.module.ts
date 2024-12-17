import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { BoardModule } from './board/module/board.module';
import { Board } from './board/entity/board.entity';

/**
 * Module
 * 애플리케이션의 기본 구성 요소로
 */

@Module({
  imports: [
    TypeOrmModule.forRoot({
      type: 'mysql',
      host: 'localhost',
      port: 3306,
      username: 'root',  // 설정한 MySQL 사용자 이름
      password: '1234',  // 설정한 MySQL 사용자 비밀번호
      database: 'spring', // 사용하려는 데이터베이스 이름
      entities: [Board], // 엔티티 연결
      synchronize: true, // 자동으로 테이블을 생성(개발용)
    }),
    BoardModule
  ]
})
export class AppModule {}
