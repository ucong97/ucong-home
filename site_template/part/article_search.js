const articleList = [];

$.get(
  'https://api.github.com/repos/vuejs/vue/commits?per_page=20&sha=master',
  {},
  function(data){
    data.forEach((row, index)=>{
      
      const article= {
        id : index+1,
        regDate:row.commit.author.date,
        writer:row.commit.author.name,
        title:row.commit.message,
        body:row.commit.message
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

      const keys = ['title', 'writer', 'body','regDate'];

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