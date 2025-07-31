let layout;
let tabCautionForm;
let uploadedButtonForm;
let uploadedGrid;
let uploadWindow;
let uploadForm;
let resultForm;
let resultGrid;
let pagination;
let defaultCompanyId;

/**
 * 레이아웃 구성
 */
const createLayout = () => {
	layout = new dhx.Layout("layout", {
		css: "contentsTitleWrap",
		header: top.menuTitle,
		cols: [
			{
				id: "main-content",
				header: BUSINESS_NAME,
				padding: 10,
				rows: [
					{
						id: "tabCautionWrap",
						height: "content",
					},
					{
						id: "uploadedWrap",
						padding: "0px",
						rows: [
							{
								id: "uploadedButtonWrap",
								height: "content",
							},
							{
								id: "uploadedGridWrap",
								padding: "10px 0px"
							},
							{
								id: "uploadedPagination",
							},
						]
					},
					{
						id: "resultWrap",
						hidden: true,
						rows: [
							{
								id: "resultFromWrap",
								height: "content",
							},
							{
								id: "resultGridWrap",
							}
						]
					}
				]
			}
		]
		
	});
}

/**
 * 상단 탭, 주의사항 폼 생성
 */
const createTabCautionForm = () => {
	const isBtoC = TENANT_CONFIG.TENANT_BUSINESS_TYPE === "B2C";
	let btocMessage = isBtoC ? "" : language.text_useCompanyMessage;
	const message1 = `◆ ${language.text_cautionMessage1}`;
	const customerMessage1 = `◆ ${btocMessage} ${language.text_cautionMessage1_tabCustomer}`;
	const customerMessage2 = `◆ ${btocMessage} ${language.text_cautionMessage2_tabCustomer}`;
	const customerMessage3 = `◆ ${btocMessage} ${language.text_cautionMessage3_tabCustomer}`;
	const counselMessage1 = `◆ ${btocMessage} ${language.text_cautionMessage1_tabCounsel}`;
	const counselMessage2 = `◆ ${btocMessage} ${language.text_cautionMessage2_tabCounsel}`;
	const counselMessage3 = `◆ ${btocMessage} ${language.text_cautionMessage3_tabCounsel}`;
	const counselMessage4 = `◆ ${btocMessage} ${language.text_cautionMessage4_tabCounsel}`;
	const counselMessage5 = `◆ ${btocMessage} ${language.text_cautionMessage5_tabCounsel}`;


	tabCautionForm = new dhx.Form(null, {
		css: "excelTabCautionForm",
		padding: 0,
		rows: [
			{
				cols: [
					{
						id: "tabKms", name: "tabKms", type: "button", value: language.tab_kms,
						css: "tabMenu selectedTab",
					},
					{
						id: "tabCompany", name: "tabCompany", type: "button", value: language.tab_company,
						css: "tabMenu", hidden: true,
					},
					{
						id: "tabCustomer", name: "tabCustomer", type: "button", value: language.tab_customer,
						css: "tabMenu",
					},
					{
						id: "tabCounsel", name: "tabCounsel", type: "button", value: language.tab_counsel,
						css: "tabMenu",
					},
				]
			},
			{
				id: "cautionMessage", name: "cautionMessage", type: "container",
				html: `
					<div class="cautionWrap">
						<p class="cautionTitle">${language.text_cautionTitle}</p>
						<p class="cautionMessage">${message1}</p>
						<p class="cautionMessage additionCustomerMessage">${customerMessage1}</p>
						<p class="cautionMessage additionCustomerMessage">${customerMessage2}</p>
						<p class="cautionMessage additionCustomerMessage">${customerMessage3}</p>
						<p class="cautionMessage additionCounselMessage">${counselMessage1}</p>
						<p class="cautionMessage additionCounselMessage">${counselMessage2}</p>
						<p class="cautionMessage additionCounselMessage">${counselMessage3}</p>
						<p class="cautionMessage additionCounselMessage">${counselMessage4}</p>
						<p class="cautionMessage additionCounselMessage">${counselMessage5}</p>
					</div>
				`,
			}
		]
	});

	layout.getCell("tabCautionWrap").attach(tabCautionForm);
}
/**
 * 업로드 이력 화면 버튼 폼 생성
 */
const createUploadedButtonForm = () => {
	uploadedButtonForm = new dhx.Form(null, {
		css: "uploadedButtonForm",
		padding: "0px 0px 5px 0px",
		cols: [
			{ type: "spacer" },
			{
				name: "linkToAddition", type: "button", value: language.button_linkToAddition,
				hidden: true, css: "uploadedButton",
			},
			{
				name: "download", type: "button", value: language.button_download,
				css: "uploadedButton",
			},
			{
				name: "excelUpload", type: "button", value: language.button_excelUpload,
				css: "uploadedButton",
			},
		]
	});
	
	layout.getCell("uploadedButtonWrap").attach(uploadedButtonForm);
}

/**
 * 업로드 이력 그리드 생성
 */
const createUploadedGrid = () => {
	uploadedGrid = new dhx.Grid(null, {
		columns: [
			{ id: "title", header: [{ text: language.gridTitle_title, align: "center" }] },
			{ id: "fileName", header: [{ text: language.gridTitle_fileName, align: "center" }] },
			{
				id: "totalCount", header: [{ text: language.gridTitle_totalCount, align: "center" }], align: "right", maxWidth: 150,
				template: (value) => {
					return comma(value);
				}
			},
			{
				id: "successCount", header: [{ text: language.gridTitle_successCount, align: "center" }], align: "right", maxWidth: 150,
				template: (value) => {
					return comma(value);
				}
			},
			{
				id: "failCount", header: [{ text: language.gridTitle_failCount, align: "center" }], align: "right",maxWidth: 150,
				template: (value) => {
					return comma(value);
				}
			},
			{
				id: "createDatetime", header: [{ text: language.gridTitle_createDatetime, align: "center" }], align: "right", minWidth: 250,
				template: (value) => {
					return getStringToDate(value) + " " + getStringToTime(value);
				}
			},
			{
				id: "detail", header: [{ text: language.gridTitle_detail, align: "center" }], align: "center",
				htmlEnable: true, maxWidth: 70,
				template: () => {
					return `<img src="image/icon/search.svg" >`;
				}
			},
		],
		autoWidth: true,
		sortable: false,
		css: "uploadGrid"
	});
	
	layout.getCell("uploadedGridWrap").attach(uploadedGrid);
}

/**
 * 엑셀 업로드 폼, 윈도우 생성
 */
const createUploadForm = () => {
	uploadWindow = new dhx.Window({
		width: 500,
		height: 300,
		title: language.windowTitle_excelUpload,
		closable: true,
		movable: true,
		modal: true,
	});
	
	uploadForm = new dhx.Form(null, {
		css: "uploadForm",
		padding: 0,
		rows: [
			{
				name: "title", type: "input", placeholder: language.placeholder_workTitle, maxlength: 50,
				label: language.label_workTitle
			},
			{
				name: "fileLabel", type: "container", css: "fileLabel",
				html: `
					<label id="fileLabel" for="excelUpload" class="dhx_label">${language.label_selectFile}</label>
				`
			},
			{
				name: "excelUpload",
				type: "container",
				html: `
					<label id="excelImport" for="excelUpload" data-dhx-id="excelImport" style="width: 100%;"
					class="dhx_button dhx_button--color_primary dhx_button--size_medium dhx_button--view_flat">
						<span class="dhx_button__text">${language.button_selectFile}</span>
					</label>
				`
			},
			{ 
				name: "uploadInput", type: "container", css: "hiddenWidget",
				html: `
					<input type="file" id="excelUpload" onchange="selectedExcelFile(event)" style="display: none;" accept=".xlsx,.xls"/>
				`,
			},
			{
				css: "fileNameForm",
				cols: [
					{
						name: "uploadFileName", type: "text", hidden: true, readonly: true,
						css: "fileName",
					},
					{
						name: "deleteFile", type: "button", hidden: true,
						icon: "dxi dxi-close", view: "link", css: "deleteFile",
					}
				]
			},
			{
				cols: [
					{ type: "spacer" },
					{ name: "upload", type: "button", value: language.button_upload }
				]
			}
		]
	});
	
	uploadWindow.attach(uploadForm);
}


/**
 * 업로드 결과 상단 폼 생성
 */
const createResultForm = () => {
	resultForm = new dhx.Form(null, {
		css: "resultForm",
		padding: 0,
		cols: [
			{
				rows: [
					{
						name: "titleLabel", type: "container",
						html: ``,
					},
					{
						name: "countLabel", type: "container",
						html: ``,
					},
				]				
			},
			{ type: "spacer" },
			{
				name: "backToUploaded", type: "button", value: language.button_backToUploaded,
				css: "resultButton",
			},
			{
				name: "excelDown", type: "button", value: language.button_excelDown,
				css: "resultButton",
			},
			{
				name: "excelReRegi", type: "button", value: language.button_excelReRegi,
				css: "resultButton",
			},
			{
				name: "delete", type: "button", value: language.button_delete,
				css: "resultButton",
			},
			{ name: "uploadId", type: "input", hidden: true },
		]
	});
	
	layout.getCell("resultFromWrap").attach(resultForm);
}

/**
 * 	Default 고객사 조회
 */
const findDefaultCompany = (from) =>{
	let data = {
		"tenantId": TENANTID,
		"searchCondition" : "findDefault",
		"from" : (GRIDLIMIT*from), 
	    "limit" : GRIDLIMIT
	}
	
	$.ajax({
		url: "company/defaultCompany",
		contentType: "application/json",
		data: data,
		method: "GET",
		async: false,
		error: (e) => {
			let status = e.status;
			errorMessage(language.errorMessage_findDefaultCompany);
		},
		success: (data) => {
			switch(data.status){
				case 0:
					errorMessage(language.failMessage_findDefaultCompany);
					break;
				case 1:
					defaultCompanyId = data.payload.data[0].companyId;
					break;
			}
		}
	});
}