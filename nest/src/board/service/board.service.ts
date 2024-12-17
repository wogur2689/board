import { Injectable, NotFoundException } from '@nestjs/common';
import { BoardDto } from '../dto/board.dto';
import { InjectRepository } from '@nestjs/typeorm';
import { FindOptionsUtils, Repository } from 'typeorm';
import { Board } from '../entity/board.entity';

@Injectable()
export class BoardService {
    constructor(
        @InjectRepository(Board)
        private boardRepository: Repository<Board>, //Repository 주입
    ) {}

    async getAllBoards(): Promise<Board[]> {
        return await this.boardRepository.find();
    }

    async getBoardById(id: number): Promise<Board> {
        const board = await this.boardRepository.findOneBy({ id });

        if (!board) {
            throw new NotFoundException(`Board with ID ${id} not found`);
        }
        
        return board;
    }

    async createBoard(boardDto: BoardDto): Promise<Board> {
        const board = this.boardRepository.create(boardDto);
        return await this.boardRepository.save(board);
    }

    async updateBoard(id: number, updateData: Partial<BoardDto>): Promise<Board> {
        const board = await this.getBoardById(id);
        Object.assign(board, updateData);
        return await this.boardRepository.save(board);
    }

    async deleteBoard(id: number): Promise<void> {
      await this.boardRepository.delete(id);
    }
}
