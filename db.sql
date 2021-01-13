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
    hitsCount INT(10) UNSIGNED NOT NULL,
    likesCount INT(10) UNSIGNED NOT NULL,
    commentsCount INT(10) UNSIGNED NOT NULL
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

# 구글 애널리틱스 4 페이지 경로별 통계 정보
CREATE TABLE ga4DataPagePath(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    pagePath CHAR(100) NOT NULL UNIQUE,
    hit INT(10) UNSIGNED NOT NULL
);

# 구글 애널리틱스 4 페이지 경로별 통계 정보
DROP TABLE IF EXISTS ga4DataPagePath;
CREATE TABLE ga4DataPagePath(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    pagePath CHAR(100) NOT NULL UNIQUE,
    hit INT(10) UNSIGNED NOT NULL
);

# 1단계, 다 불러오기
SELECT pagePath
FROM ga4DataPagePath AS GA4_PP
WHERE GA4_PP.pagePath LIKE '/article_detail_%.html%';

# 2단계, pagePath 정제
SELECT 
IF(
    INSTR(GA4_PP.pagePath, '?') = 0,
    GA4_PP.pagePath,
    SUBSTR(GA4_PP.pagePath, 1, INSTR(GA4_PP.pagePath, '?') - 1)
) AS pagePathWoQueryStr
FROM ga4DataPagePath AS GA4_PP
WHERE GA4_PP.pagePath LIKE '/article_detail_%.html%';

# 3단계, pagePathWoQueryStr(정제된 pagePth)기준으로 sum
SELECT 
IF(
    INSTR(GA4_PP.pagePath, '?') = 0,
    GA4_PP.pagePath,
    SUBSTR(GA4_PP.pagePath, 1, INSTR(GA4_PP.pagePath, '?') - 1)
) AS pagePathWoQueryStr,
SUM(GA4_PP.hit) AS hit
FROM ga4DataPagePath AS GA4_PP
WHERE GA4_PP.pagePath LIKE '/article_detail_%.html%'
GROUP BY pagePathWoQueryStr;

# 4단계, subQuery를 이용
SELECT *
FROM (
    SELECT 
    IF(
        INSTR(GA4_PP.pagePath, '?') = 0,
        GA4_PP.pagePath,
        SUBSTR(GA4_PP.pagePath, 1, INSTR(GA4_PP.pagePath, '?') - 1)
    ) AS pagePathWoQueryStr,
    SUM(GA4_PP.hit) AS hit
    FROM ga4DataPagePath AS GA4_PP
    WHERE GA4_PP.pagePath LIKE '/article_detail_%.html%'
    GROUP BY pagePathWoQueryStr
) AS GA4_PP;

# 5단계, subQuery를 이용해서 나온결과를 다시 재편집
SELECT CAST(REPLACE(REPLACE(GA4_PP.pagePathWoQueryStr, "/article_detail_", ""), ".html", "") AS UNSIGNED) AS articleId,
hit
FROM (
    SELECT 
    IF(
        INSTR(GA4_PP.pagePath, '?') = 0,
        GA4_PP.pagePath, 
        SUBSTR(GA4_PP.pagePath, 1, INSTR(GA4_PP.pagePath, '?') - 1)
    ) AS pagePathWoQueryStr,
    SUM(GA4_PP.hit) AS hit
    FROM ga4DataPagePath AS GA4_PP
    WHERE GA4_PP.pagePath LIKE '/article_detail_%.html%'
    GROUP BY pagePathWoQueryStr
) AS GA4_PP;

# 구글 애널리틱스에서 가져온 데이터를 기반으로 모든 게시물의 hit 정보를 갱신

SELECT AR.id, AR.hitsCount, GA4_PP.hit
FROM article AS AR
INNER JOIN (
    SELECT CAST(REPLACE(REPLACE(GA4_PP.pagePathWoQueryStr, '/article_detail_', ''), '.html', '') AS UNSIGNED) AS articleId,
    hit
    FROM (
        SELECT 
        IF(
            INSTR(GA4_PP.pagePath, '?') = 0,
            GA4_PP.pagePath,
            SUBSTR(GA4_PP.pagePath, 1, INSTR(GA4_PP.pagePath, '?') - 1)
        ) AS pagePathWoQueryStr,
        SUM(GA4_PP.hit) AS hit
        FROM ga4DataPagePath AS GA4_PP
        WHERE GA4_PP.pagePath LIKE '/article_detail_%.html%'
        GROUP BY pagePathWoQueryStr
    ) AS GA4_PP
) AS GA4_PP
ON AR.id = GA4_PP.articleId;

# 2단계, 실제 쿼리
UPDATE article AS AR
INNER JOIN (
    SELECT CAST(REPLACE(REPLACE(GA4_PP.pagePathWoQueryStr, '/article_detail_', ''), '.html', '') AS UNSIGNED) AS articleId,
    hit
    FROM (
        SELECT 
        IF(
            INSTR(GA4_PP.pagePath, '?') = 0,
            GA4_PP.pagePath,
            SUBSTR(GA4_PP.pagePath, 1, INSTR(GA4_PP.pagePath, '?') - 1)
        ) AS pagePathWoQueryStr,
        SUM(GA4_PP.hit) AS hit
        FROM ga4DataPagePath AS GA4_PP
        WHERE GA4_PP.pagePath LIKE '/article_detail_%.html%'
        GROUP BY pagePathWoQueryStr
    ) AS GA4_PP
) AS GA4_PP
ON AR.id = GA4_PP.articleId
SET AR.hitsCount = GA4_PP.hit;

# 태그 테이블
CREATE TABLE tag(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    relTypeCode CHAR(20) NOT NULL,
    relId INT(10) UNSIGNED NOT NULL,
    `body` CHAR(20) NOT NULL
);

# 아래 쿼리와 관련된 인덱스걸기
# select * from tag where relTypeCode = 'article' and `body` = 'SQL';
ALTER TABLE textBoard.tag ADD INDEX (`relTypeCode`,`body`);

#아 래 쿼리와 관련된 인덱스걸기
# 중복된 데이터 생성 금지
# select * from tag where relTypeCode = 'article';
# select * from tag where relTypeCode = 'article' and relId = 5;
# select * from tag where relTypeCode = 'article' and relId = 5 and `body` = 'SQL';
ALTER TABLE textBoard.tag ADD UNIQUE INDEX (`relTypeCode`,`relId`,`body`);

# 2번글에 SQL과, INSERT, DB 라는 태그 걸기
INSERT INTO tag
SET regdate=NOW(),
    updateDate=NOW(),
    relTypeCode='article',
    relId=2,
    `body`='SQL';
    
INSERT INTO tag
SET regdate=NOW(),
    updateDate=NOW(),
    relTypeCode='article',
    relId=2,
    `body`='INSERT';
    
INSERT INTO tag
SET regdate=NOW(),
    updateDate=NOW(),
    relTypeCode='article',
    relId=2,
    `body`='DB';

# 게시물 + 태그정보
SELECT A.id,A.title,IFNULL(GROUP_CONCAT(T.body),"") AS tags
FROM article AS A
LEFT JOIN tag AS T
ON A.id = T.relId
AND T.relTypeCode ='article'
GROUP BY a.id;