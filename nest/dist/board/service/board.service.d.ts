import { BoardDto } from '../dto/board.dto';
import { Repository } from 'typeorm';
import { Board } from '../entity/board.entity';
export declare class BoardService {
    private boardRepository;
    constructor(boardRepository: Repository<Board>);
    getAllBoards(): Promise<Board[]>;
    getBoardById(id: number): Promise<Board>;
    createBoard(boardDto: BoardDto): Promise<Board>;
    updateBoard(id: number, updateData: Partial<BoardDto>): Promise<Board>;
    deleteBoard(id: number): Promise<void>;
}
