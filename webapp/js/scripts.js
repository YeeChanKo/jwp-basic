$(".qna-comment").on("click", ".answerWrite input[type=submit]", addAnswer);

function addAnswer(e) {
	e.preventDefault();

	var queryString = $("form[name=answer]").serialize();

	$.ajax({
		type : 'post',
		url : '/api/qna/addAnswer',
		data : queryString,
		dataType : 'json',
		error : onError,
		success : function(json, status) {
			var answer = json.answer;
			var answerTemplate = $("#answerTemplate").html();
			var template = answerTemplate.format(answer.writer, new Date(
					answer.createdDate), answer.contents, answer.answerId,
					answer.answerId);
			$(".qna-comment-slipp-articles").prepend(template);

			$(".answerWrite .form-control").val("");

			var question = json.question;
			$(".qna-comment-count strong").text(question.countOfComment);
		},
	});
}

function onError(xhr, status) {
	alert("error");
}

$(".qna-comment").on("click", ".form-delete button", deleteAnswer);

function deleteAnswer(e) {
	e.preventDefault();

	var queryString = $(e.target).closest(".form-delete").serialize();

	$.ajax({
		type : 'post',
		url : '/api/qna/deleteAnswer',
		data : queryString,
		dataType : 'json',
		error : onError,
		success : function(json, status) {
			if (json.result.status) {
				$(e.target).closest("article").remove();
				$(".qna-comment-count strong").text(
						$(".qna-comment-count strong").text() * 1 - 1);
			} else {
				alert(json.result.message);
			}
		},
	});
}

String.prototype.format = function() {
	var args = arguments;
	return this.replace(/{(\d+)}/g, function(match, number) {
		return typeof args[number] != 'undefined' ? args[number] : match;
	});
};