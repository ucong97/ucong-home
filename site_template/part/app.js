
function ArticleDetail__Body__init() {
	if (toastui === undefined) {
		return;
	}

	// 유튜브 플러그인 시작
	function youtubePlugin() {
		toastui.Editor.codeBlockManager.setReplacer('youtube', youtubeId => {
			// Indentify multiple code blocks
			const wrapperId = `yt${Math.random().toString(36).substr(2, 10)}`;

			// Avoid sanitizing iframe tag
			setTimeout(renderYoutube.bind(null, wrapperId, youtubeId), 0);

			return `<div id="${wrapperId}"></div>`;
		});
	}

	function renderYoutube(wrapperId, youtubeId) {
		const el = document.querySelector(`#${wrapperId}`);

		el.innerHTML = `<div class="toast-ui-youtube-plugin-wrap"><iframe src="https://www.youtube.com/embed/${youtubeId}" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe></div>`;
	}
	// 유튜브 플러그인 끝
	var body = document.querySelector('.article-detail__body');
	var initialValue = body.innerHTML.trim();
	
	var viewer = new toastui.Editor.factory({
		el: body,
		initialValue: initialValue,
		viewer: true,
		plugins: [toastui.Editor.plugin.codeSyntaxHighlight,youtubePlugin]
	});
}

ArticleDetail__Body__init();
