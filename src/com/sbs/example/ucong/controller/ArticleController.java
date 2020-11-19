package com.sbs.example.ucong.controller;

import java.util.List;
import java.util.Scanner;

import com.sbs.example.ucong.container.Container;
import com.sbs.example.ucong.dto.Article;
import com.sbs.example.ucong.dto.ArticleReply;
import com.sbs.example.ucong.dto.Board;
import com.sbs.example.ucong.dto.Member;
import com.sbs.example.ucong.service.ArticleService;
import com.sbs.example.ucong.service.MemberService;

public class ArticleController extends Controller {
	private Scanner sc;
	private ArticleService articleService;
	private MemberService memberService;

	public ArticleController() {
		sc = Container.scanner;
		articleService = Container.articleService;
		memberService = Container.memberService;
	}

	public void doCommand(String cmd) {
		if (cmd.equals("article list")) {
			showList(cmd);
		} else if (cmd.startsWith("article detail")) {
			showDetail(cmd);
		} else if (cmd.startsWith("article delete")) {
			doDelete(cmd);
		} else if (cmd.startsWith("article modify")) {
			doModify(cmd);
		} else if (cmd.equals("article write")) {
			doWrite(cmd);
		} else if (cmd.equals("article makeBoard")) {
			doMakeBoard(cmd);
		} else if (cmd.startsWith("article selectBoard")) {
			doSelectBoard(cmd);
		} else if (cmd.startsWith("article writeReply")) {
			doWriteReply(cmd);
		} else if (cmd.startsWith("article modifyReply")) {
			doModifyReply(cmd);
		} else if (cmd.startsWith("article deleteReply")) {
			doDeleteReply(cmd);
		}else if (cmd.startsWith("article recommand")) {
			doRecommand(cmd);
		}else if (cmd.startsWith("article cancleRecommand")) {
			doCancleRecommand(cmd);
		}
	}

	private void doCancleRecommand(String cmd) {
		if (Container.session.logouted()) {
			System.out.println("로그인하고 이용해주세요.");
			return;
		}
		System.out.println("== 게시물 추천취소 ==");
		String[] cmdBits = cmd.split(" ");
		if (cmdBits.length <= 2) {
			System.out.println("추천하실 게시물의 번호를 입력해주세요.");
			return;
		}
		int articleId = Integer.parseInt(cmdBits[2]);
		Article article = articleService.getArticle(articleId);
		if (article == null) {
			System.out.println("게시물이 존재하지 않습니다.");
			return;
		}
		int memberId = Container.session.loginedMemberId;
		
		boolean cancleRecommandAble= articleService.cancleRecommand(articleId,memberId);
		if(cancleRecommandAble==false) {
			System.out.println("추천되있지 않습니다.");
			return;
		}
		
		System.out.printf("%d번 게시물을 추천취소하셨습니다!\n",articleId);
	}

	private void doRecommand(String cmd) {
		if (Container.session.logouted()) {
			System.out.println("로그인하고 이용해주세요.");
			return;
		}
		System.out.println("== 게시물 추천 ==");
		String[] cmdBits = cmd.split(" ");
		if (cmdBits.length <= 2) {
			System.out.println("추천하실 게시물의 번호를 입력해주세요.");
			return;
		}
		int articleId = Integer.parseInt(cmdBits[2]);
		Article article = articleService.getArticle(articleId);
		if (article == null) {
			System.out.println("게시물이 존재하지 않습니다.");
			return;
		}
		int memberId = Container.session.loginedMemberId;
		
		boolean recommandAble = articleService.recommand(articleId,memberId);
		if (recommandAble ==false) {
			System.out.println("이미 추천되어있습니다.");
			return;
		}
		
		System.out.printf("%d번 게시물을 추천하셨습니다!  \n",articleId);
	}

	private void doDeleteReply(String cmd) {
		if (Container.session.logouted()) {
			System.out.println("로그인하고 이용해주세요.");
			return;
		}
		System.out.println("== 댓글 삭제 ==");
		String[] cmdBits = cmd.split(" ");
		if (cmdBits.length <= 2) {
			System.out.println("댓글 번호를 입력해주세요.");
			return;
		}
		int inputedId = Integer.parseInt(cmdBits[2]);

		ArticleReply articleReply = articleService.getArticleReply(inputedId);
		int memberId = Container.session.loginedMemberId;
		if (articleReply == null) {
			System.out.println("해당 댓글이 존재하지 않습니다.");
			return;
		}
		if (articleReply.memberId != memberId) {
			System.out.println("댓글 삭제 권한이 없습니다.");
			return;
		}

		articleService.replyDelete(inputedId);

		System.out.printf("%d번 댓글이 삭제되었습니다.\n", inputedId);

	}

	private void doModifyReply(String cmd) {
		if (Container.session.logouted()) {
			System.out.println("로그인하고 이용해주세요.");
			return;
		}
		System.out.println("== 댓글 수정 ==");
		String[] cmdBits = cmd.split(" ");
		if (cmdBits.length <= 2) {
			System.out.println("댓글 번호를 입력해주세요.");
			return;
		}
		int inputedId = Integer.parseInt(cmdBits[2]);

		ArticleReply articleReply = articleService.getArticleReply(inputedId);
		int memberId = Container.session.loginedMemberId;
		if (articleReply == null) {
			System.out.println("해당 댓글이 존재하지 않습니다.");
			return;
		}
		if (articleReply.memberId != memberId) {
			System.out.println("댓글 수정 권한이 없습니다.");
			return;
		}

		System.out.printf("내용 : ");
		String body = sc.nextLine();

		articleService.replyModify(inputedId, body);
		System.out.printf("%d번 댓글이 수정되었습니다.\n", inputedId);
	}

	private void doWriteReply(String cmd) {
		if (Container.session.logouted()) {
			System.out.println("로그인하고 이용해주세요.");
			return;
		}
		System.out.println("== 댓글 작성 ==");
		String[] cmdBits = cmd.split(" ");
		if (cmdBits.length <= 2) {
			System.out.println("게시물 번호를 입력해주세요.");
			return;
		}
		int articleId = Integer.parseInt(cmdBits[2]);
		Article article = articleService.getArticle(articleId);
		if (article == null) {
			System.out.println("게시물이 존재하지 않습니다.");
			return;
		}
		System.out.printf("내용 : ");
		String body = sc.nextLine();

		int memberId = Container.session.loginedMemberId;
		int id = articleService.WrtieReply(articleId, memberId, body);

		System.out.printf("%d번글에 %d번 댓글이 추가되었습니다.\n", articleId, id);
	}

	private void doSelectBoard(String cmd) {
		System.out.println("== 게시판 선택 ==");
		String[] cmdBits = cmd.split(" ");
		if (cmdBits.length <= 2) {
			System.out.println("게시판 번호를 입력해주세요.");
			return;
		}
		String boardCode = cmdBits[2];

		Board board = articleService.getBoardByBoardCode(boardCode);
		if (board == null) {
			System.out.println("존재하지 않는 게시판입니다.\n");
			return;
		}
		Container.session.setCurrentBoardCode(boardCode);
		System.out.printf("%s(%d번)게시판이 선택되었습니다.\n", board.name, board.id);
	}

	private void doMakeBoard(String cmd) {
		if (Container.session.logouted()) {
			System.out.println("로그인하고 이용해주세요.");
			return;
		}
		int memberId = Container.session.loginedMemberId;
		Member member = memberService.getMemberById(memberId);
		if(member.isAdmin()==false) {
			System.out.println("관리자만 생성 가능합니다.");
			return;
		}
		System.out.println("== 게시판 생성 ==");
		System.out.printf("게시판 이름 : ");
		String name = sc.nextLine();

		int id = articleService.makeBoard(name);
		System.out.printf("%s(%d번) 게시물이 생성되었습니다.\n", name, id);
	}

	private void doWrite(String cmd) {
		if (Container.session.logouted()) {
			System.out.println("로그인하고 이용해주세요.");
			return;
		}
		System.out.println("== 게시물 작성 ==");
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();

		int memberId = Container.session.loginedMemberId;
		int boardId = 1;//임시

		int id = articleService.write(memberId, boardId, title, body);
		System.out.printf("%d번 게시물이 생성되었습니다.\n", id);
	}

	private void doModify(String cmd) {
		if (Container.session.logouted()) {
			System.out.println("로그인하고 이용해주세요.");
			return;
		}
		System.out.println("== 게시물 수정 ==");
		String[] cmdBits = cmd.split(" ");
		if (cmdBits.length <= 2) {
			System.out.println("게시물 번호를 입력해주세요.");
			return;
		}
		int inputedId = Integer.parseInt(cmdBits[2]);

		Article article = articleService.getArticle(inputedId);
		int memberId = Container.session.loginedMemberId;
		if (article == null) {
			System.out.println("게시물이 존재하지 않습니다.");
			return;
		}
		if (article.memberId != memberId) {
			System.out.println("게시물 수정 권한이 없습니다.");
			return;
		}
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();

		articleService.modify(inputedId, title, body);
		System.out.printf("%d번 게시물이 수정되었습니다.\n", inputedId);
	}

	private void doDelete(String cmd) {
		if (Container.session.logouted()) {
			System.out.println("로그인하고 이용해주세요.");
			return;
		}
		String[] cmdBits = cmd.split(" ");
		if (cmdBits.length <= 2) {
			System.out.println("게시물 번호를 입력해주세요.");
			return;
		}
		int inputedId = Integer.parseInt(cmdBits[2]);
		Article article = articleService.getArticle(inputedId);
		int memberId = Container.session.loginedMemberId;
		if (article == null) {
			System.out.println("게시물이 존재하지 않습니다.");
			return;
		}
		if (article.memberId != memberId) {
			System.out.println("게시물 삭제 권한이 없습니다.");
			return;
		}

		System.out.println("== 게시물 삭제 ==");

		articleService.delete(inputedId);

		System.out.printf("%d번 게시물이 삭제되었습니다.\n", inputedId);
	}

	private void showDetail(String cmd) {
		System.out.println("== 게시물 상세보기 ==");
		String[] cmdBits = cmd.split(" ");
		if (cmdBits.length <= 2) {
			System.out.println("게시물 번호를 입력해주세요.");
			return;
		}
		int inputedId = Integer.parseInt(cmdBits[2]);

		articleService.hitCount(inputedId);

		Article article = articleService.getArticle(inputedId);

		if (article == null) {
			System.out.println("게시물이 존재하지 않습니다.");
			return;
		}
		int recommandCount = articleService.getRecommandCount(inputedId);
		Member member = memberService.getMemberById(article.memberId);
		System.out.printf("번호 : %d\n", article.id);
		System.out.printf("작성날짜 : %s\n", article.regDate);
		System.out.printf("수정날짜 : %s\n", article.updateDate);
		System.out.printf("작성자 : %s\n", member.name);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("내용 : %s\n", article.body);
		System.out.printf("조회수 : %d\n", article.hit);
		System.out.printf("추천수 : %d\n",recommandCount);

		System.out.println("===============댓글리스트===============");
		List<ArticleReply> articleReplys = articleService.getArticleReplysByArticleId(inputedId);
		if (articleReplys.size() == 0) {
			System.out.println("등록된 댓글이 없습니다.");
			return;
		}
		for (ArticleReply articleReply : articleReplys) {
			Member replyMember = memberService.getMemberById(articleReply.memberId);
			System.out.printf("%s 회원님 : %s\n", replyMember.name, articleReply.body);
		}

	}

	private void showList(String cmd) {
		String BoardCode = Container.session.getCurrentBoardCode;
		Board board = articleService.getBoardByBoardCode(BoardCode);
		System.out.printf("== %s 리스트 ==\n",board.name);
		System.out.println("번호 / 작성 / 수정 / 작성자 / 제목 / 조회수 / 추천수 ");

		List<Article> articles = articleService.getForPrintArticles(board.id);

		for (Article article : articles) {
			int recommandCount = articleService.getRecommandCount(article.id);
			System.out.printf("%d / %s / %s / %s / %s / %d / %d \n", article.id, article.regDate,
					article.updateDate, article.extra__memberName, article.title,article.hit,recommandCount);
		}

	}
}
