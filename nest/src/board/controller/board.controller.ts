import { Body, Controller, Delete, Get, Param, Post, Put, Render } from '@nestjs/common';
import { BoardService } from '../service/board.service';
import { BoardDto } from '../dto/board.dto';

@Controller('board')
export class BoardController {
    constructor(private readonly boardService: BoardService) {}

    @Get()
    @Render('board/index') // EJS 템플릿 렌더링
    async getAllBoards() {
      const boards = await this.boardService.getAllBoards();
      console.log({boards});
      return { boards }; // EJS에서 사용할 데이터 반환
    }

    @Get(':id')
    async view(@Param('id') id: number) {
        return await this.boardService.getBoardById(+id);
    }

    @Post()
    async insert(@Body() boardDto: BoardDto) {
        return await this.boardService.createBoard(boardDto);
    }

    @Put(':id')
    async update(@Param('id') id:number, @Body() updateData: Partial<BoardDto>) {
        return await this.boardService.updateBoard(+id, updateData);
    }

    @Delete(':id')
    async delete(@Param('id') id:number) {
        return await this.boardService.deleteBoard(+id);
    }
}
