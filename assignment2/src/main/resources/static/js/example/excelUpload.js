// 엑셀 업로드 JS
let selectedTab = "tabKms";
let kmsHeadList = [];
let additionData;

const init = () => {
	findDefaultCompany(0);
	createLayout();
	createTabCautionForm();
	createUploadedButtonForm();
	createUploadedGrid();
	createUploadForm();
	createResultForm();
	
	setTabCautionFormEvent();
	setUploadedButtonFormEvent();
	setUploadedGridEvent();
	setUploadFormEvent();
	setResultFormEvent();
	
	findUploadHistory(0);
	console.log(TENANT_CONFIG)
	console.log(TENANT_CONFIG.TENANT_BUSINESS_TYPE)

	if(TENANT_CONFIG.TENANT_BUSINESS_TYPE === "B2C"){
		console.log("b2c")
		tabCautionForm.getItem("tabCompany").hide()
	}
	
	top.layout.getCell("contents").progressHide();
}

/**
 * 탭 폼 이벤트
 */
const setTabCautionFormEvent = () => {
	tabCautionForm.events.on("click", (id, event) => {
		$(".tabMenu").removeClass("selectedTab");
		$("#"+id).parent().addClass("selectedTab");
		
		selectedTab = id;
		
		selectAdditionForm();
		
		changeTab();
	});
}

/**
 * 업로드 이력 화면 버튼 폼 이벤트
 */
const setUploadedButtonFormEvent = () => {
	uploadedButtonForm.events.on("click", (id, event) => {
		switch(id) {
			case "linkToAddition":
				showConfirm(language.confirmTitle_linkToAddition, language.confirmMessage_linkToAddition, () => {
					linkToAddition();					
				});
				break;
			case "download":
				excelDownload();
				break;
			case "excelUpload":
				uploadForm.getItem("title").setValue("");
				uploadWindow.show();
				dhx.awaitRedraw().then(() => {
					$("#excelUpload").val("").trigger("change");					
				});
				break;
		}
	});
}

/**
 * 업로드 이력 그리드 이벤트
 */
const setUploadedGridEvent = () => {
	uploadedGrid.events.on("cellClick", (row, col) => {
		if(col.id !== "detail") return;
		
		layout.getCell("uploadedWrap").hide();
		layout.getCell("resultWrap").show();
		resultForm.getItem("uploadId").setValue(row.uploadId);
		
		findUploadedDetail(row.uploadId);
	});
}

/**
 * 엑셀 업로드 팝업 폼 이벤트
 */
const setUploadFormEvent = () => {
	uploadForm.events.on("click", (id, event) => {
		switch(id) {
			case "deleteFile":
				$("#excelUpload").val("").trigger("change");
				break;
			case "upload":
				if(!uploadValidate()) return;
				excelUpload();
				break;
		}
	});
}

/**
 * 엑셀 업로드 전 유효성 검사
 * @return 검사 결과 true/false
 */
const uploadValidate = () => {
	if(isNULL(uploadForm.getItem("title").getValue())) {
		errorMessage(language.errorMessage_enterTitle);
		return false;
	}
	
	if(isNULL(document.getElementById("excelUpload").files[0])) {
		errorMessage(language.errorMessage_uploadExcelFile);
		return false;
	}
	
	return true;
}

/**
 * 업로드 파일 선택 onChange 이벤트
 * @param event 업로드 input event
 */
const selectedExcelFile = (event) => {
	const file = event.target.files[0];
	
	if(isNULL(file)) {
		uploadForm.getItem("uploadFileName").setValue("");
		uploadForm.getItem("excelUpload").show();
		$(".fileNameForm").hide();
		uploadForm.getItem("uploadFileName").hide();
		uploadForm.getItem("deleteFile").hide();
	}else {
		uploadForm.getItem("uploadFileName").setValue(file.name);
		uploadForm.getItem("excelUpload").hide();
		$(".fileNameForm").show();
		uploadForm.getItem("uploadFileName").show();
		uploadForm.getItem("deleteFile").show();
	}
}

/**
 * 업로드 결과 상단 폼 이벤트
 */
const setResultFormEvent = () => {
	resultForm.events.on("click", (id, event) => {
		let uploadId = resultForm.getItem("uploadId").getValue();
		
		switch(id) {
			case "backToUploaded":
				layout.getCell("resultWrap").hide();
				layout.getCell("uploadedWrap").show();
				break;
			case "excelDown":
                fileDownload("excelUpload/failDown/"+uploadId+"/"+LANGUAGE_CODE);
				break;
			case "excelReRegi":
				excelReRegister(uploadId);
				break;
			case "delete":
				excelUploadFailDelete();
				break;
		}
	});
}

/**
 * 탭 전환 이벤트
 */
const changeTab = () => {
	switch(selectedTab) {
		case "tabKms":
			$(".additionCustomerMessage").hide();
			$(".additionCounselMessage").hide();
			uploadedButtonForm.getItem("linkToAddition").hide();
			break;
		case "tabCompany":
		case "tabCustomer":
			$(".additionCustomerMessage").show();
			$(".additionCounselMessage").hide();
			uploadedButtonForm.getItem("linkToAddition").show();
			break;
		case "tabCounsel":
			$(".additionCustomerMessage").hide();
			$(".additionCounselMessage").show();
			uploadedButtonForm.getItem("linkToAddition").show();
			break;
	}

	layout.getCell("resultWrap").hide();
	layout.getCell("uploadedWrap").show();

	findUploadHistory(0);
}

/**
 * 탭 메뉴 별 Addition 메뉴 이동
 */
const linkToAddition = () => {
	let addition = "";
	
	switch(selectedTab) {
		case "tabCompany":
			addition = "companyAddition";
			break;
		case "tabCustomer":
			addition = "customerAddition";
			break;
		case "tabCounsel":
			addition = "counselAddition";
			break;
	}
	
	const excelUploadMenu = top.sidebar.data.find({ by: "url", match: "excelUpload" });
	const additionMenu = top.sidebar.data.find({ by: "url", match: addition });
	const additionParentMenu = top.sidebar.data.find({ by: "id", match: additionMenu.parent });
	
	excelUploadMenu.html = top.createMenuHtml(excelUploadMenu, false);
	additionParentMenu.html = top.createMenuHtml(additionParentMenu, true);
	additionMenu.html= top.createMenuHtml(additionMenu, true);
	
	top.pageMove(additionMenu.id);
}

/**
 * 탭 메뉴 별 양식 다운로드
 */
const excelDownload = () => {
	top.layout.progressShow();
	
	switch(selectedTab) {
		case "tabKms":
        	fileDownload("kms/excelFormDownload");
			break;
		case "tabCompany":
            fileDownload("company/"+TENANTID+"/"+LANGUAGE_CODE);
			break;
		case "tabCustomer":
            fileDownload("customer/"+TENANTID+"/"+LANGUAGE_CODE);
			break;
		case "tabCounsel":
            fileDownload("counsel/"+TENANTID+"/"+LANGUAGE_CODE);
			break;
	}
	
	top.layout.progressHide();
}

/**
 * KMS 업로드 이력 조회
 */
const findUploadHistory = (from) => {
	let type = "";
	
	switch(selectedTab) {
		case "tabKms":
			type = "KMS";
			break;
		case "tabCompany":
			type = "COMPANY";
			break;
		case "tabCustomer":
			type = "CUSTOMER";
			break;
		case "tabCounsel":
			type = "COUNSEL";
			break;
	}
	
	let data = {
		tenantId: TENANTID,
		from: (GRIDLIMIT*from),
		limit: GRIDLIMIT,
		uploadType: type
	};
	
	if(pagination != null){
		pagination.setPage(from);
		pagination.destructor();
	}
	
	$.ajax({
		url: "excelUpload/history",
		method: "GET",
		data: data,
		contentsType: "application/json",
		async: false,
		error: () => {
			let status = e.status;
			errorMessage(language.errorMessage_findUploadHistory);
		},
		success: (data) => {
			if(data.payload.total_count === 0) {
				beforeDataGrid(uploadedGrid);
							
				dhx.awaitRedraw().then(() => {
					$(".uploadGrid .dhx_grid-body").addClass("noneData");							
				});
				
				noneDataGrid(uploadedGrid);				
			}else{
				$(".uploadGrid .dhx_grid-body").removeClass("noneData");
				uploadedGrid.data.parse(data.payload);
			}
			
			pagination = new dhx.Pagination(null, {
			    css: "pagination dhx_widget--bordered dhx_widget--no-border_top",
			    data: uploadedGrid.data,
			    pageSize: GRIDLIMIT
			});
			
			layout.getCell("uploadedPagination").attach(pagination);
			pagination.setPage(from);

			pagination.events.on("change", (index, previousIndex) => {
				findUploadHistory(index);
			});
		}
	});
}

/**
 * 실패 이력 상세보기
 */
const findUploadedDetail = (uploadId) => {
	
	$.ajax({
		url: "excelUpload/history/"+uploadId,
		method: "GET",
		contentsType: "application/json",
		async: false,
		error: () => {
			let status = e.status;
			errorMessage(language.errorMessage_findUploadedDetail);
		},
		success: (data) => {
			let noneData = data.payload.contents.length <= 0;
			detailScreenSet(data.payload.contents, data.payload.data, noneData);
		}
	});
}

/**
 * 	그리드 전체 선택 이벤트
 */
const master_ch = (state) => {
    resultGrid.data.forEach((row) => {
		if(!row.$empty) {
	        resultGrid.data.update(row.id, { "checkBox": state });			
		}
	});
}

/**
 * 동적폼 조회
 */
const selectAdditionForm = () => {
	let mode = "";
	
	switch(selectedTab) {
		case "tabKms":
			return;
		case "tabCompany":
			mode = "company";
			break;
		case "tabCustomer":
			mode = "customer";
			break;
		case "tabCounsel":
			mode = "counsel";
			break;
	}
	
	$.ajax({
		url: "addition/"+mode,
		contentType: "applciation/json",
		data: {
			tenantId: TENANTID,
			languageCode: LANGUAGE_CODE
		},
		method: "GET",
		async: false,
		error: (e) => {
			return;
		},
		success: (data) => {
			additionData = data.payload.filter((item) =>
				item.useFlag === "Y"
				&& item.additionType != "LINE" && item.additionType != "LABEL"
				&& item.additionType.indexOf("ADDRESS") < 0);
		}
	});
}

const formatHeader = (label, isRequired) => {
	return `<span>${label}</span>` + (isRequired ? "<span class='requireHedaer'>*</span>" : "");
};

const isRequiredField = (string) => {
	// tabKms일 경우 Category1이나 Subject이면 필수 필드
	if (selectedTab === "tabKms") {
		return string === "Category1" || string === "Subject" || string === "Content";
	}

	// 추가 데이터가 있을 경우 필수 여부를 체크
	if (isNOTNULL(additionData)) {
		return additionData.some(addition => 
			string === addition.additionLabel && addition.requireFlag === "Y"
		);
	}

	return false;
};

/**
 * 상세보기 그리드 컬럼 생성
 */
const setResultGridColumns = (header) => {
	if(resultGrid) resultGrid.destructor();
	
	let columns = [
		{ id: "checkBox", header: [{ text: 
			"<input id='masterCheckbox' type='checkbox' onclick='master_ch(checked)' style='zoom: 1.8;'></input>",
			align: "center" }], type: "boolean", editable: true, maxWidth: 70
		},
	];
	
	header.forEach(string => {
		const isRequired = isRequiredField(string);
		const name = formatHeader(string, isRequired);
	
		columns.push({
			id: string, 
			header: [{ text: name, align: "center" }]
		});
	});
	
	columns.push({
		id: "reason", header: [{ text: "실패 사유", align: "center" }], editable: false
	});
	
	columns.push({
		id: "failId", header: [{ text: "" }], hidden: true
	});
	
	let resultGridConfig = {
		columns: columns,
		leftSplit: 1,
		editable: true,
		sortable: false,
	    tooltip: true,
	    autoWidth: true,
	    css: "resultGrid",
	};
	
	resultGrid = new dhx.Grid(null, resultGridConfig);
	
	resultGrid.events.on("cellClick", (event, value) => {
        if(value.id == "checkBox"){
			if(!event.checkBox) {
				$("#masterCheckbox").prop("checked", false);
			}else{
				let rowsSelected = resultGrid.data.findAll({by:"checkBox", match: true});
				
				if(resultGrid.config.data.length === rowsSelected.length) {
					$("#masterCheckbox").prop("checked", true);
				}
			}
			return false;
		}
	});
	
	layout.getCell("resultGridWrap").attach(resultGrid);
}

/**
 * 실패 이력 재등록
 */
const excelReRegister = (uploadId) => {
	let url = "";
	let data = {};
	let list = resultGrid.data.findAll({by:"checkBox", match: true});
	
	if(isNULL(list)) {
		errorMessage(language.errorMessage_selectReRegisterRow);
		return;
	}
	
	list.forEach((item) => {
		delete item.$height;
		delete item.checkBox;
		delete item.id;
		delete item.reason;
	});
	
	let gridData = [list];
	
	switch(selectedTab) {
		case "tabKms":
			url = "kms/excelreregi";
			data = {
				excelFile : JSON.stringify(gridData),
				uploadId: uploadId,
				language: LANGUAGE_CODE,
				tenantId: TENANTID,
				creatorId: ADMIN_CREATOR_ID,
				creatorName: ADMIN_CREATOR_NAME,
			};
			break;
		case "tabCompany":
			url = "company/excelreregi";
			data = {
				excelFile : JSON.stringify(gridData)
				,uploadId: uploadId
				,tenantId: TENANTID
				,creatorId: ADMIN_CREATOR_ID
				,creatorName: ADMIN_CREATOR_NAME
				,language: LANGUAGE_CODE
			};
			break;
		case "tabCustomer":
			url = "customer/excelreregi";
			data = {
				excelFile : JSON.stringify(gridData)
				,uploadId: uploadId
				,tenantId: TENANTID
				,creatorId: ADMIN_CREATOR_ID
				,creatorName: ADMIN_CREATOR_NAME
				,language: LANGUAGE_CODE
				,defaultCompany: defaultCompanyId
			};
			break;
		case "tabCounsel":
			url = "counsel/excelreregi";
			data = {
				excelFile : JSON.stringify(gridData)
				,uploadId: uploadId
				,tenantId: TENANTID
				,creatorId: ADMIN_CREATOR_ID
				,creatorName: ADMIN_CREATOR_NAME
				,language: LANGUAGE_CODE
			};
			break;
	}
	
	excelUploadAjax(url, data);
}

/**
 * 엑셀 실패 목록 삭제 세팅
 */
const excelUploadFailDelete = () => {
	let list = resultGrid.data.findAll({by:"checkBox", match: true});
	
	if(isNULL(list)) {
		errorMessage(language.errorMessage_selectDeleteRow);
		return;
	}
	
	showConfirm(language.confirmTitle_deleteHistory, language.confirmMessage_deleteHistory, () => {
		let failList = [];
		
		list.forEach((item) => {
			failList.push(item.failId);
		});
		
		deleteFailList(failList);
	});
}

/**
 * 엑셀 실패 목록 삭제
 */
const deleteFailList = (failList) => {
	let uploadId = resultForm.getItem("uploadId").getValue();
	
	let data = {
		uploadId: uploadId,
		deleteList: failList.toString()
	}
	
	$.ajax({
		url: "excelUpload/fail",
		contentType: "application/json",
		data: JSON.stringify(data),
		method: "PUT",
		async: false,
		error: (e) => {
			let status = e.status;
			errorMessage(language.errorMessage_deleteHistory);
		}, success: (data) => {
			findUploadHistory(0);
			let noneData = data.payload.contents.length <= 0;
			detailScreenSet(data.payload.contents, data.payload.data, noneData);
		}
	})
}

/*
*	lawrence
*	엑셀 업로드 SheetJS
*/
const excelUpload = () => {
	top.layout.progressShow();
	
	const file = document.getElementById("excelUpload").files[0];
	const fileType = file.name.split(".")[1];
	
	if(fileType != 'xlsx' && fileType != 'xls'){
		top.layout.progressHide();
		errorMessage(language.errorMessage_uploadOnlyExcel);
		$("#excelUpload").val("").trigger("change");
		return false;
	}else{
		excelExportCommon(file, handleExcelDataAll);
	}
}

const excelExportCommon = (file, callback) => {
    let reader = new FileReader();
    reader.onload = () => {
        let fileData = reader.result;
        let wb = XLSX.read(fileData, {type : 'binary'});
		callback(wb.SheetNames, wb.Sheets);
    };
    reader.readAsBinaryString(file);
}

const handleExcelDataAll = (sheetNames, sheetDatas) => {
	handleExcelDataJson(sheetNames, sheetDatas); // json 형태
}

const handleExcelDataJson = (sheetNames, sheetDatas) => {
	let list = [];
	
	sheetNames.forEach((item) => {
		// 원시 데이터 대신 포맷된 문자열을 얻기 위해 raw: false 옵션을 추가
		let config = {};
		if(selectedTab === "tabKms") {
			config = {raw: false};
		}else{
			config = {raw: false, defval: ""};
		}
		
		let data = XLSX.utils.sheet_to_json(sheetDatas[item], config);
		
		if(selectedTab === "tabKms" && isNOTNULL(Object.keys(data[0]))) {
			kmsHeadList = Object.keys(data[0]);
		}
		
		data = data.map(row => {
			return Object.keys(row).reduce((formattedRow, key) => {
				const value = row[key];
				
				formattedRow[key] = isDate(value)
						? formatDate(value)
						: isTime(value)
								? formatTime(value)
								: (value === "" ? "" : value);
						
				return formattedRow;
			}, {});
		});
        
		list.push(data);
	});
	
	if(isNULL(list)) {
		top.layout.progressHide();
		errorMessage(language.errorMessage_emptyFile);
		$("#excelUpload").val("").trigger("change");
		return;
	}
	
	excelSave(list);
}

// 날짜 형식 데이터인지 확인
const isDate = (value) => {
	const dateRegex = /^\d{1,2}\/\d{1,2}\/\d{2}$/;
	
	if (!dateRegex.test(value)) {
		return false;
	}
	
	let parts = value.split('/');
	
	let day = parts[0].padStart(2, '0');
	let month = parts[1].padStart(2, '0');
	let year = parts[2];
	
	if(year.length === 2) {
		year = '20' + year;
	}
	
	let dateValue = `${year}-${month}-${day}`;
	
	const datePattern = /^\d{4}-\d{2}-\d{2}$/; // 기본적인 'YYYY-MM-DD' 형식
	return datePattern.test(dateValue);
}

// 날짜 형식으로 변환하는 함수
const formatDate = (value) => {
	const date = new Date(value);
	let month = '' + (date.getMonth() + 1),
		day = '' + date.getDate(),
		year = date.getFullYear();

	if (month.length < 2) 
		month = '0' + month;
	if (day.length < 2) 
		day = '0' + day;

	return [year, month, day].join('-');
}

// 시간 형식 데이터인지 확인
const isTime = (value) => {
	const timePattern = /^([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$/;
	return timePattern.test(value);
}

// 시간 형식으로 변환하는 함수
const formatTime = (value) => {
	const date = new Date(`1970-01-01T${value}Z`);

	let hours = '' + date.getUTCHours(),
		minutes = '' + date.getUTCMinutes(),
		seconds = '' + date.getUTCSeconds();

	if (hours.length < 2) { hours = '0' + hours; }
	if (minutes.length < 2) { minutes = '0' + minutes; }
	if (seconds.length < 2) { seconds = '0' + seconds; }

    return [hours, minutes, seconds].join(':');
}

const excelSave = (excelFile) => {
	let url = "";
	let data = {};
	const fileName = document.getElementById("excelUpload").files[0].name;
	
	switch(selectedTab) {
		case "tabKms":
			url = "kms/excelUpload";
			data = {
				excelFile : JSON.stringify(excelFile),
				tenantId: TENANTID,
				creatorId: ADMIN_CREATOR_ID,
				creatorName: ADMIN_CREATOR_NAME,
				language: LANGUAGE_CODE,
				title: uploadForm.getItem("title").getValue(),
				fileName: fileName,
				headerString: kmsHeadList.toString(),
			};
			break;
		case "tabCompany":
			url = "company/excelUpload";
			data = {
				excelFile : JSON.stringify(excelFile)
				,tenantId: TENANTID
				,creatorId: ADMIN_CREATOR_ID
				,creatorName: ADMIN_CREATOR_NAME
				,language: LANGUAGE_CODE
				,businessType: TENANT_CONFIG.TENANT_BUSINESS_TYPE
				,title: uploadForm.getItem("title").getValue()
				,fileName: fileName
			};
			break;
		case "tabCustomer":
			url = "customer/excelUpload";
			data = {
				excelFile : JSON.stringify(excelFile)
				,tenantId: TENANTID
				,creatorId: ADMIN_CREATOR_ID
				,creatorName: ADMIN_CREATOR_NAME
				,language: LANGUAGE_CODE
				,title: uploadForm.getItem("title").getValue()
				,fileName: fileName
				,defaultCompany: defaultCompanyId
			};
			break;
		case "tabCounsel":
			url = "counsel/excelUpload";
			data = {
				excelFile : JSON.stringify(excelFile)
				,tenantId: TENANTID
				,creatorId: ADMIN_CREATOR_ID
				,creatorName: ADMIN_CREATOR_NAME
				,language: LANGUAGE_CODE
				,title: uploadForm.getItem("title").getValue()
				,fileName: fileName
				,businessType: TENANT_CONFIG.TENANT_BUSINESS_TYPE
			};
			break;
	}
	
	excelUploadAjax(url, data);
}

/**
 *  엑셀 업로드 Ajax 생성
 */
const excelUploadAjax = (url, param) => {
	$.ajax({
		url: url,
		traditional: true,
		data: JSON.stringify(param),
		method:"POST",
		dataType:"json",
		contentType: "application/json",
		async: false,
		error: (e) => {
			let message = "";
			
			switch(e.responseJSON.status) {
				case -4051:
					message = language.errorMessage_notSupportedFile;
					break;
				case -4005:
					message = language.errorMessage_formMismatch;
					$("#excelUpload").val("").trigger("change");
					break;
				default:
					message = language.errorMessage_excelUpload;
			}
			
			errorMessage(message);
			top.layout.progressHide();
		},
		success: (data) => {
			top.layout.progressHide();
			
			uploadWindow.hide();
			
			findUploadHistory(0);
			let noneData = data.payload.result.contents.length <= 0;
			detailScreenSet(data.payload.result.contents, data.payload.result.data, noneData);
			
			if(data.payload.haveFailList === "Y") {
				errorMessage(language.errorMessage_haveFailList);
			}else {
				message(language.message_uploadExcel);
			}
			
			kmsHeadList = [];
		}
	});
}

/*
*	lawrence
*	엑셀 업로드 끝
*/

const detailScreenSet = (contents, uploadData, noneData) => {
	layout.getCell("uploadedWrap").hide();
	layout.getCell("resultWrap").show();
	resultForm.getItem("uploadId").setValue(uploadData.uploadId);
	
	detailBasicSet(uploadData);
	
	if(noneData) {
		beforeDataGrid(resultGrid);
					
		dhx.awaitRedraw().then(() => {
			$(".resultGrid .dhx_grid-body").addClass("noneData");							
		});
		
		noneDataGrid(resultGrid, language.gridMessage_allDataSuccess);
		
		resultForm.getItem("excelDown").hide();
		resultForm.getItem("excelReRegi").hide();
		resultForm.getItem("delete").hide();
	}else{
		resultForm.getItem("excelDown").show();
		resultForm.getItem("excelReRegi").show();
		resultForm.getItem("delete").show();
		
		$(".resultGrid .dhx_grid-body").removeClass("noneData");
		
		let gridData = [];
		
		contents.forEach((item) => {
			item.contents["failId"] = item.failId;
			gridData.push(item.contents);
		});
		
		dhx.awaitRedraw().then(() => {
			resultGrid.data.parse(gridData);					
		});
	}
}

/**
 * 실패 이력 상세보기 페이지 기본 세팅
 */
const detailBasicSet = (data) => {
	const nameLabel = `
		<span class="resultLabel">${language.labelSpan_workTitle} : </span>
		<span class="resultValue">${data.title}</span>
	`;
		
	const countLabel = `
		<span class="resultLabel">${language.labelSpan_total} : </span>
		<span class="resultValue">${comma(data.totalCount)}</span>
		<span class="resultLabel">${language.labelSpan_success} : </span>
		<span class="resultValue">${comma(data.successCount)}</span>
		<span class="resultLabel">${language.labelSpan_fail} : </span>
		<span class="resultValue">${comma(data.failCount)}</span>
	`;
	
	resultForm.getItem("titleLabel").attachHTML(nameLabel);
	resultForm.getItem("countLabel").attachHTML(countLabel);
	
	setResultGridColumns(data.header);
}