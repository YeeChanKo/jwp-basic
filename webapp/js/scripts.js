String.prototype.format = function() {
	var args = arguments;
	return this.replace(/{(\d+)}/g, function(match, number) {
		return typeof args[number] != "undefined" ? args[number] : match;
	});
};

$(".answerWrite input[type=submit]").on("click", addAnswer);

function addAnswer(e) {
	e.preventDefault();

	var queryString = $("form[name=answer]").serialize();

	$.ajax({
		type : "post",
		url : "/qna/addanswer",
		data : queryString,
		dataType : "json",
		error : function(xhr, status) {
			alert("error");
		},
		success : function(json, status) {
			var answerTemplate = $("#answerTemplate").html();
			var template = answerTemplate.format(json.writer, new Date(
					json.createdDate), json.contents, json.answerId,
					json.answerId);
			$(".qna-comment-slipp-articles").prepend(template);
		}
	});
}

$(".qna-comment-slipp-articles").on("click",
		".form-delete button[type='submit']", deleteAnswer);

function deleteAnswer(e) {
	e.preventDefault();

	var deleteBtn = $(this);
	var queryString = deleteBtn.closest("form").serialize();

	$.ajax({
		type : "post",
		url : deleteBtn.closest("form").prop("action"),
		data : queryString,
		dataType : "json",
		error : function(xhr, status) {
			alert("error");
		},
		success : function(json, status) {
			if (json.status) {
				deleteBtn.closest("article").remove();
			}
		}
	});
}
