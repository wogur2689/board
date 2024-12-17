import { BoardService } from '../service/board.service';
import { BoardDto } from '../dto/board.dto';
export declare class BoardController {
    private readonly boardService;
    constructor(boardService: BoardService);
    getAllBoards(): Promise<{
        boards: import("../entity/board.entity").Board[];
    }>;
    view(id: number): Promise<import("../entity/board.entity").Board>;
    insert(boardDto: BoardDto): Promise<import("../entity/board.entity").Board>;
    update(id: number, updateData: Partial<BoardDto>): Promise<import("../entity/board.entity").Board>;
    delete(id: number): Promise<void>;
}
