# 게시물 + 태그정보
SELECT A.id,A.title,IFNULL(GROUP_CONCAT(T.body),"") AS tags
FROM article AS A
LEFT JOIN tag AS T
ON A.id = T.relId
AND T.relTypeCode ='article'
GROUP BY a.id;