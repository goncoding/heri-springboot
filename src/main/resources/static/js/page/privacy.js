(function () {
	$(document).ready(function () {
		var privacyVersionSelectElement = $('#privacy-version');
		privacyVersionSelectElement.find('option[value="'+location.pathname+'"]').attr('selected', true);
		privacyVersionSelectElement.selectmenu({
			select: function (event, ui) {
				location.href = ui.item.value;
			}
		});
	});
})();
