
// 전역 변수
let layout;
let filterForm;
let userlistGrid;
let pagination;
const pageSize = 10;
let currentParams = "";
let ignorePageEvent = false;   // → 이 플래그로 루프를 방지


//레이아웃 생성
function createLayout(rootId = "userlistform") {
  layout = new dhx.Layout(document.getElementById(rootId), {
    rows: [
      { id: "filtercontent",   height: "content" },
      { id: "userlistcontent", height: "*" },
      { id: "paginationcontent", height: 60 }
    ]
  });
}

// 필터 폼 생성
function createFilterForm() {
  filterForm = new dhx.Form(null, {
    rows: [{
      cols: [
        { type: "input",      name: "id",           placeholder: "ID",    width: 80 },
        { type: "input",      name: "name",         placeholder: "이름",   width: 80 },
        { type: "combo",      name: "level",        placeholder: "레벨",   width: 80,
            data: ["A","B","C","D","E","F"].map(v => ({ id: v, value: v })) },
        { type: "input",      name: "desc",         placeholder: "설명",   width:100 },
        { type: "datepicker", name: "startRegDate", placeholder: "시작일", dateFormat: "%Y-%m-%d", width:120 },
        { type: "datepicker", name: "endRegDate",   placeholder: "종료일", dateFormat: "%Y-%m-%d", width:120 },
        { type: "button",     name: "search", id:"search", text:"조회",  color:"primary",   size:"medium" },
        { type: "button",     name: "reset",        text:"초기화", color:"secondary", size:"medium" }
      ]
    }]
  });
  layout.getCell("filtercontent").attach(filterForm);

  // 조회 버튼
  filterForm.getItem("search").events.on("click", () => {
    currentParams = new URLSearchParams(filterForm.getValue()).toString();
    loadUserList(0, currentParams);
  });
  // 초기화 버튼
  filterForm.getItem("reset").events.on("click", () => {
    filterForm.clear();
    currentParams = "";
    loadUserList(0, "");
  });
}


// 그리드 생성
function createUserlistGrid() {
  userlistGrid = new dhx.Grid(null, {
    columns: [
      { id:"id",     header:[{text:"ID"}] },
      { id:"name",   header:[{text:"이름"}] },
      { id:"level",  header:[{text:"레벨"}] },
      { id:"desc",   header:[{text:"설명"}] },
      { id:"regDate",header:[{text:"가입일"}] }
    ],
    autoWidth:true, resizable:true, height:442, data:[]
  });
  layout.getCell("userlistcontent").attach(userlistGrid);
}


// 페이징 컴포넌트 생성 & 이벤트 등록 (한 번만)
function createPagination(dataStore) {
  if (pagination) pagination.destructor();
  pagination = new dhx.Pagination(null, {
    css: "dhx_widget--bordered dhx_widget--no-border_top",
    data: dataStore,
    pageSize
  });
  layout.getCell("paginationcontent").attach(pagination);

  pagination.events.on("change", newPage => {
    if (ignorePageEvent) return;          // <- 여기서 무한 루프 방지
    loadUserList(newPage, currentParams);
    console.log("dddddddddddddddd")
  });
}

//  데이터 로딩 함수
function loadUserList(page = 0, params = "") {
  const url = `/user/list/${page}` + (params ? `?${params}` : "");
//  userlistGrid.showProgress();           // 로딩 인디케이터

  fetch(url)
    .then(res => {
      if (!res.ok) throw new Error(`HTTP ${res.status}`);
      return res.json();
    })
    .then(data => {
    console.log("aaaaaaaaaaaaaaaa")
      // 그리드 갱신
      console.log("data.content", data.content)
      userlistGrid.data.parse(data.content);
      userlistGrid.data.totalCount = data.totalElements;
      userlistGrid.data.getLength = function() {
          return this.totalCount;
      };

      // 페이지 번호 세팅 (이때만 silent 플래그 사용)
      ignorePageEvent = true;
      pagination.setPage(data.number, true);
      ignorePageEvent = false;
    })
    .catch(err => {
      console.error("loadUserList 오류:", err);
      dhx.alert({ title:"오류", text:"유저리스트 로딩에 실패했습니다." });
    })
    .finally(() => {
//      userlistGrid.hideProgress();        // 로딩 끝
    });
}

// 초기화
function init() {
  createLayout();
  createFilterForm();
  createUserlistGrid();
  createPagination(userlistGrid.data);
}

document.addEventListener("DOMContentLoaded", init);
