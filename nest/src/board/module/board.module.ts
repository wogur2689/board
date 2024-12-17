import { Module } from '@nestjs/common';
import { BoardController } from '../controller/board.controller';
import { BoardService } from '../service/board.service';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Board } from '../entity/board.entity';

@Module({
  imports: [TypeOrmModule.forFeature([Board])],  // Board 엔티티를 사용하기 위해 추가
  controllers: [BoardController],
  providers: [BoardService]
})
export class BoardModule {}
