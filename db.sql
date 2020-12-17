#DB생성
DROP DATABASE IF EXISTS textBoard;
CREATE DATABASE textBoard;
USE textBoard;

#게시물 테이블 생성
CREATE TABLE article(
    id INT(10) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(100) NOT NULL,
    `body` TEXT NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    boardId INT(10) UNSIGNED NOT NULL,
    hit INT(10) UNSIGNED NOT NULL
);

# 게시물 데이터 3개 생성
INSERT INTO article
SET regDate = NOW(),
    updateDate = NOW(),
    title = '제목1',
    `body` = '내용1',
    memberId = 1,
    boardId =1;
 
INSERT INTO article
SET regDate = NOW(),
    updateDate = NOW(),
    title = '제목2',
    `body` = '내용2',
    memberId = 1,
    boardId =1;
    
INSERT INTO article
SET regDate = NOW(),
    updateDate = NOW(),
    title = '제목3',
    `body` = '내용3',
    memberId = 1,
    boardId =1;

CREATE TABLE `member`(
    id INT(10) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(30) NOT NULL,
    loginPw VARCHAR(50) NOT NULL,
    `name` CHAR(30) NOT NULL
);
INSERT INTO `member`
SET regDate = NOW(),
    updateDate = NOW(),
    loginId = 'aa',
    loginPw = 'aa',
    `name`= 'aa';

INSERT INTO `member`
SET regDate = NOW(),
    updateDate = NOW(),
    loginId = 'bb',
    loginPw = 'bb',
    `name`= 'bb';

CREATE TABLE board(
    id INT(10) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
    regDate DATETIME,
    updateDate DATETIME,
    `name` CHAR(20) NOT NULL,
    `code` CHAR(20) NOT NULL
);
INSERT INTO board
SET regDate = NOW(),
    updateDate = NOW(),
    `name` = '공지사항',
    `code`= 'notice';

INSERT INTO board
SET regDate = NOW(),
    updateDate = NOW(),
    `name` = '자유',
    `code`= 'free';

CREATE TABLE articleReply(
    id INT(10) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    `body` TEXT NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    articleId INT(10) UNSIGNED NOT NULL
);
CREATE TABLE recommand(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    articleId INT(10) UNSIGNED NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL
);


# 게시물 랜덤 생성

INSERT INTO article
SET regDate = NOW(),
    updateDate = NOW(),
    title = CONCAT('제목_',RAND()),
    `body` = CONCAT('내용_',RAND()),
    memberId = IF(RAND()>0.5,1,2),
    boardId =IF(RAND()>0.5,1,2);

# 1번글 내용을 마크다운 문법으로 수정

UPDATE article 
SET `body` = '# 안녕\r\n# 나는\r\n# 김유경\r\n- 하하\r\n- 호호\r\n- 즐거워' 
WHERE id = '1'; 

# 2번글 내용에 자바소스코드 넣기
UPDATE article 
SET `body` = '# 자바기본문법\r\n```java\r\nint a = 10;\r\nint b = 20;\r\nint c = a + b ; // 결과값 30\r\n```' 
WHERE id = '2';


