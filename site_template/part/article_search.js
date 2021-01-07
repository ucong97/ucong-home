const articleList = [];

$.get(
  'article_list.json',
  {},
  function(data){
    data.forEach((row, index)=>{
      
      const article= {
        id : row.id,
        regDate:row.regDate,
        writer:row.extra__memberName,
        title:row.title,
        hit_count:row.hitCount,
		likes_count:row.likesCount
      };
      
      articleList.push(article);
    });
  },
  'json'
);

const articleListBox = new Vue({
  el: "#article-list-wrap",
  data: {
    articleList: articleList,
    searchKeyword: ""
  },
  methods:{
    searchKeywordInputed:_.debounce(function(e){
      this.searchKeyword = e.target.value;
    },500)
  },
  computed: {
    filterKey: function () {
      return this.searchKeyword.toLowerCase();
    },
    filterd: function () {
      if (this.filterKey.length == 0) {
        return this.articleList;
      }

      const keys = ['title', 'writer','regDate'];

      return this.articleList.filter((row) => {
        //v6
        const match = keys.some((key)=>{
            if (row[key].toLowerCase().indexOf(this.filterKey) > -1) {
            return true;
          }
        });
        return match;
        
      });
    }
  }
});