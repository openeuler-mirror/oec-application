/* eslint-disable */
require('script-loader!file-saver');
require('./Blob.js');
require('script-loader!xlsx/dist/xlsx.core.min');
// 生成数据
function createArray(table) {
  // 输出集合
  var outPut = [];
  var rangeArr = [];
  var rowArr = table.querySelectorAll('tr');
  // 循环每一行
  for (var rowItem = 0; rowItem < rowArr.length; ++rowItem) {
    var outRow = [];
    var currentRow = rowArr[rowItem];
    var columnArr = currentRow.querySelectorAll('td');
    // 循环当前行内所有单元格
    for (var columnItem = 0; columnItem < columnArr.length; ++columnItem) {
      var currentCell = columnArr[columnItem];
      var colspan = currentCell.getAttribute('colspan');
      var rowspan = currentCell.getAttribute('rowspan');
      // 当前单元格内容
      var cellValue = currentCell.innerText;
      // 判空处理
      if (cellValue !== "" && cellValue == +cellValue) cellValue = +cellValue;

      rangeArr.forEach(function (range) {
        if (rowItem >= range.s.r && rowItem <= range.e.r && outRow.length >= range.s.c && outRow.length <= range.e.c) {
          for (var i = 0; i <= range.e.c - range.s.c; ++i) outRow.push(null);
        }
      });

      if (rowspan || colspan) {
        rowspan = rowspan || 1;
        colspan = colspan || 1;
        rangeArr.push({ s: { r: rowItem, c: outRow.length }, e: { r: rowItem + rowspan - 1, c: outRow.length + colspan - 1 } });
      }
      ;

      outRow.push(cellValue !== "" ? cellValue : null);

      if (colspan) for (var k = 0; k < colspan - 1; ++k) outRow.push(null);
    }
    outPut.push(outRow);
  }
  return [outPut, rangeArr];
};

function treatmentDate(v, date) {
  if (date) v += 1462;
  var epoch = Date.parse(v);
  return (epoch - new Date(Date.UTC(1899, 11, 30))) / (24 * 60 * 60 * 1000);
}

function sheet_from_array_of_arrays(data, opts) {
  var ws = {};
  var range = { s: { c: 10000000, r: 10000000 }, e: { c: 0, r: 0 } };
  for (var rowItem = 0; rowItem != data.length; ++rowItem) {
    for (var columnItem = 0; columnItem != data[rowItem].length; ++columnItem) {
      if (range.s.r > rowItem) range.s.r = rowItem;
      if (range.s.c > columnItem) range.s.c = columnItem;
      if (range.e.r < rowItem) range.e.r = rowItem;
      if (range.e.c < columnItem) range.e.c = columnItem;
      var currentCell = { v: data[rowItem][columnItem] };
      if (currentCell.v == null) continue;
      var cell_ref = XLSX.utils.encode_cell({ c: columnItem, r: rowItem });

      if (typeof currentCell.v === 'number') currentCell.t = 'n';
      else if (typeof currentCell.v === 'boolean') currentCell.t = 'b';
      else if (currentCell.v instanceof Date) {
        currentCell.t = 'n';
        currentCell.z = XLSX.SSF._table[14];
        currentCell.v = treatmentDate(currentCell.v);
      }
      else currentCell.t = 's';

      ws[cell_ref] = currentCell;
    }
  }
  if (range.s.c < 10000000) ws['!ref'] = XLSX.utils.encode_range(range);
  return ws;
}

function Workbook() {
  if (!(this instanceof Workbook)) return new Workbook();
  this.SheetNames = [];
  this.Sheets = {};
}

function s2ab(s) {
  var buf = new ArrayBuffer(s.length);
  var view = new Uint8Array(buf);
  for (var i = 0; i != s.length; ++i) view[i] = s.charCodeAt(i) & 0xFF;
  return buf;
}

export function export_table_to_excel(id) {
  var theTable = document.getElementById(id);
  var oo = createArray(theTable);
  var rangeArr = oo[1];

  var data = oo[0];
  var ws_name = "SheetJS";

  var wb = new Workbook(), ws = sheet_from_array_of_arrays(data);

  ws['!merges'] = rangeArr;

  wb.SheetNames.push(ws_name);
  wb.Sheets[ws_name] = ws;

  var wbout = XLSX.write(wb, { bookType: 'xlsx', bookSST: false, type: 'binary' });

  saveAs(new Blob([s2ab(wbout)], { type: "application/octet-stream" }), "test.xlsx")
}

function formatJson(jsonData) {
}
export function export_json_to_excel(th, jsonData, defaultTitle) {

  var data = jsonData;
  data.unshift(th);
  var ws_name = "SheetJS";

  var wb = new Workbook(), ws = sheet_from_array_of_arrays(data);
  wb.SheetNames.push(ws_name);
  wb.Sheets[ws_name] = ws;

  var wbout = XLSX.write(wb, { bookType: 'xlsx', bookSST: false, type: 'binary' });
  var title = defaultTitle || '列表'
  saveAs(new Blob([s2ab(wbout)], { type: "application/octet-stream" }), title + ".xlsx")
}
