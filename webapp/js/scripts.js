$(document).ready(function() {/* jQuery toggle layout */
	$('#btnToggle').click(function() {
		if ($(this).hasClass('on')) {
			$('#main .col-md-6').addClass('col-md-4').removeClass('col-md-6');
			$(this).removeClass('on');
		} else {
			$('#main .col-md-4').addClass('col-md-6').removeClass('col-md-4');
			$(this).addClass('on');
		}
	});
});

// 동적으로 선택된 li 클래스 토글해주기 위한 글로벌 함수
var changeNavBarActive = function(input) {
	var selected = $("#navbar-collapse2 #" + input);
	selected.closest("ul").find(".active").toggleClass("active");
	selected.toggleClass("active");
};