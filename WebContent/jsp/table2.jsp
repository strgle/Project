<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/header/meta.jsp"%>
<title>Insert title here</title>
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/default.jsp"%>
<%@include file="/common/header/layui.jsp"%>
</head>
<body>
<div id="tablediv">

	
		<div class="layui-form layui-border-box layui-table-view"
			lay-filter="LAY-table-1" style="width: 892px; height: 332px;">
			<div class="layui-table-box">
				<div class="layui-table-header">
					<table class="layui-table" border="0" cellspacing="0"
						cellpadding="0">
						<thead>
							<tr>
								<th data-field="id"><div
										class="layui-table-cell laytable-cell-1-id">
										<span>ID</span><span class="layui-table-sort layui-inline"><i
											class="layui-edge layui-table-sort-asc"></i><i
											class="layui-edge layui-table-sort-desc"></i></span>
									</div></th>
								<th data-field="username"><div
										class="layui-table-cell laytable-cell-1-username">
										<span>用户名</span>
									</div></th>
								<th data-field="sex"><div
										class="layui-table-cell laytable-cell-1-sex">
										<span>性别</span><span class="layui-table-sort layui-inline"><i
											class="layui-edge layui-table-sort-asc"></i><i
											class="layui-edge layui-table-sort-desc"></i></span>
									</div></th>
								<th data-field="city"><div
										class="layui-table-cell laytable-cell-1-city">
										<span>城市</span>
									</div></th>
								<th data-field="sign"><div
										class="layui-table-cell laytable-cell-1-sign">
										<span>签名</span>
									</div></th>
								<th data-field="experience"><div
										class="layui-table-cell laytable-cell-1-experience">
										<span>积分</span><span class="layui-table-sort layui-inline"><i
											class="layui-edge layui-table-sort-asc"></i><i
											class="layui-edge layui-table-sort-desc"></i></span>
									</div></th>
								<th data-field="score"><div
										class="layui-table-cell laytable-cell-1-score">
										<span>评分</span><span class="layui-table-sort layui-inline"><i
											class="layui-edge layui-table-sort-asc"></i><i
											class="layui-edge layui-table-sort-desc"></i></span>
									</div></th>
								<th data-field="classify"><div
										class="layui-table-cell laytable-cell-1-classify">
										<span>职业</span>
									</div></th>
								<th data-field="wealth"><div
										class="layui-table-cell laytable-cell-1-wealth">
										<span>财富</span><span class="layui-table-sort layui-inline"><i
											class="layui-edge layui-table-sort-asc"></i><i
											class="layui-edge layui-table-sort-desc"></i></span>
									</div></th>
								<th class="layui-table-patch"><div class="layui-table-cell"
										style="width: 16px;"></div></th>
							</tr>
						</thead>
					</table>
				</div>
				<div class="layui-table-body layui-table-main"
					style="height: 251px;">
					<table class="layui-table" border="0" cellspacing="0"
						cellpadding="0">
						<tbody>
							<tr data-index="0" class="">
								<td data-field="id"><div
										class="layui-table-cell laytable-cell-1-id">10000</div></td>
								<td data-field="username"><div
										class="layui-table-cell laytable-cell-1-username">user-0</div></td>
								<td data-field="sex"><div
										class="layui-table-cell laytable-cell-1-sex">女</div></td>
								<td data-field="city"><div
										class="layui-table-cell laytable-cell-1-city">城市-0</div></td>
								<td data-field="sign"><div
										class="layui-table-cell laytable-cell-1-sign">签名-0</div></td>
								<td data-field="experience"><div
										class="layui-table-cell laytable-cell-1-experience">255</div></td>
								<td data-field="score"><div
										class="layui-table-cell laytable-cell-1-score">57</div></td>
								<td data-field="classify"><div
										class="layui-table-cell laytable-cell-1-classify">作家</div></td>
								<td data-field="wealth"><div
										class="layui-table-cell laytable-cell-1-wealth">82830700</div></td>
							</tr>
							<tr data-index="1" class="">
								<td data-field="id"><div
										class="layui-table-cell laytable-cell-1-id">10001</div></td>
								<td data-field="username"><div
										class="layui-table-cell laytable-cell-1-username">user-1</div></td>
								<td data-field="sex"><div
										class="layui-table-cell laytable-cell-1-sex">男</div></td>
								<td data-field="city"><div
										class="layui-table-cell laytable-cell-1-city">城市-1</div></td>
								<td data-field="sign"><div
										class="layui-table-cell laytable-cell-1-sign">签名-1</div></td>
								<td data-field="experience"><div
										class="layui-table-cell laytable-cell-1-experience">884</div></td>
								<td data-field="score"><div
										class="layui-table-cell laytable-cell-1-score">27</div></td>
								<td data-field="classify"><div
										class="layui-table-cell laytable-cell-1-classify">词人</div></td>
								<td data-field="wealth"><div
										class="layui-table-cell laytable-cell-1-wealth">64928690</div></td>
							</tr>
							<tr data-index="2" class="">
								<td data-field="id"><div
										class="layui-table-cell laytable-cell-1-id">10002</div></td>
								<td data-field="username"><div
										class="layui-table-cell laytable-cell-1-username">user-2</div></td>
								<td data-field="sex"><div
										class="layui-table-cell laytable-cell-1-sex">女</div></td>
								<td data-field="city"><div
										class="layui-table-cell laytable-cell-1-city">城市-2</div></td>
								<td data-field="sign"><div
										class="layui-table-cell laytable-cell-1-sign">签名-2</div></td>
								<td data-field="experience"><div
										class="layui-table-cell laytable-cell-1-experience">650</div></td>
								<td data-field="score"><div
										class="layui-table-cell laytable-cell-1-score">31</div></td>
								<td data-field="classify"><div
										class="layui-table-cell laytable-cell-1-classify">酱油</div></td>
								<td data-field="wealth"><div
										class="layui-table-cell laytable-cell-1-wealth">6298078</div></td>
							</tr>
							<tr data-index="3" class="">
								<td data-field="id"><div
										class="layui-table-cell laytable-cell-1-id">10003</div></td>
								<td data-field="username"><div
										class="layui-table-cell laytable-cell-1-username">user-3</div></td>
								<td data-field="sex"><div
										class="layui-table-cell laytable-cell-1-sex">女</div></td>
								<td data-field="city"><div
										class="layui-table-cell laytable-cell-1-city">城市-3</div></td>
								<td data-field="sign"><div
										class="layui-table-cell laytable-cell-1-sign">签名-3</div></td>
								<td data-field="experience"><div
										class="layui-table-cell laytable-cell-1-experience">362</div></td>
								<td data-field="score"><div
										class="layui-table-cell laytable-cell-1-score">68</div></td>
								<td data-field="classify"><div
										class="layui-table-cell laytable-cell-1-classify">诗人</div></td>
								<td data-field="wealth"><div
										class="layui-table-cell laytable-cell-1-wealth">37117017</div></td>
							</tr>
							<tr data-index="4" class="">
								<td data-field="id"><div
										class="layui-table-cell laytable-cell-1-id">10004</div></td>
								<td data-field="username"><div
										class="layui-table-cell laytable-cell-1-username">user-4</div></td>
								<td data-field="sex"><div
										class="layui-table-cell laytable-cell-1-sex">男</div></td>
								<td data-field="city"><div
										class="layui-table-cell laytable-cell-1-city">城市-4</div></td>
								<td data-field="sign"><div
										class="layui-table-cell laytable-cell-1-sign">签名-4</div></td>
								<td data-field="experience"><div
										class="layui-table-cell laytable-cell-1-experience">807</div></td>
								<td data-field="score"><div
										class="layui-table-cell laytable-cell-1-score">6</div></td>
								<td data-field="classify"><div
										class="layui-table-cell laytable-cell-1-classify">作家</div></td>
								<td data-field="wealth"><div
										class="layui-table-cell laytable-cell-1-wealth">76263262</div></td>
							</tr>
							<tr data-index="5" class="">
								<td data-field="id"><div
										class="layui-table-cell laytable-cell-1-id">10005</div></td>
								<td data-field="username"><div
										class="layui-table-cell laytable-cell-1-username">user-5</div></td>
								<td data-field="sex"><div
										class="layui-table-cell laytable-cell-1-sex">女</div></td>
								<td data-field="city"><div
										class="layui-table-cell laytable-cell-1-city">城市-5</div></td>
								<td data-field="sign"><div
										class="layui-table-cell laytable-cell-1-sign">签名-5</div></td>
								<td data-field="experience"><div
										class="layui-table-cell laytable-cell-1-experience">173</div></td>
								<td data-field="score"><div
										class="layui-table-cell laytable-cell-1-score">87</div></td>
								<td data-field="classify"><div
										class="layui-table-cell laytable-cell-1-classify">作家</div></td>
								<td data-field="wealth"><div
										class="layui-table-cell laytable-cell-1-wealth">60344147</div></td>
							</tr>
							<tr data-index="6" class="">
								<td data-field="id"><div
										class="layui-table-cell laytable-cell-1-id">10006</div></td>
								<td data-field="username"><div
										class="layui-table-cell laytable-cell-1-username">user-6</div></td>
								<td data-field="sex"><div
										class="layui-table-cell laytable-cell-1-sex">女</div></td>
								<td data-field="city"><div
										class="layui-table-cell laytable-cell-1-city">城市-6</div></td>
								<td data-field="sign"><div
										class="layui-table-cell laytable-cell-1-sign">签名-6</div></td>
								<td data-field="experience"><div
										class="layui-table-cell laytable-cell-1-experience">982</div></td>
								<td data-field="score"><div
										class="layui-table-cell laytable-cell-1-score">34</div></td>
								<td data-field="classify"><div
										class="layui-table-cell laytable-cell-1-classify">作家</div></td>
								<td data-field="wealth"><div
										class="layui-table-cell laytable-cell-1-wealth">57768166</div></td>
							</tr>
							<tr data-index="7">
								<td data-field="id"><div
										class="layui-table-cell laytable-cell-1-id">10007</div></td>
								<td data-field="username"><div
										class="layui-table-cell laytable-cell-1-username">user-7</div></td>
								<td data-field="sex"><div
										class="layui-table-cell laytable-cell-1-sex">男</div></td>
								<td data-field="city"><div
										class="layui-table-cell laytable-cell-1-city">城市-7</div></td>
								<td data-field="sign"><div
										class="layui-table-cell laytable-cell-1-sign">签名-7</div></td>
								<td data-field="experience"><div
										class="layui-table-cell laytable-cell-1-experience">727</div></td>
								<td data-field="score"><div
										class="layui-table-cell laytable-cell-1-score">28</div></td>
								<td data-field="classify"><div
										class="layui-table-cell laytable-cell-1-classify">作家</div></td>
								<td data-field="wealth"><div
										class="layui-table-cell laytable-cell-1-wealth">82030578</div></td>
							</tr>
							<tr data-index="8">
								<td data-field="id"><div
										class="layui-table-cell laytable-cell-1-id">10008</div></td>
								<td data-field="username"><div
										class="layui-table-cell laytable-cell-1-username">user-8</div></td>
								<td data-field="sex"><div
										class="layui-table-cell laytable-cell-1-sex">男</div></td>
								<td data-field="city"><div
										class="layui-table-cell laytable-cell-1-city">城市-8</div></td>
								<td data-field="sign"><div
										class="layui-table-cell laytable-cell-1-sign">签名-8</div></td>
								<td data-field="experience"><div
										class="layui-table-cell laytable-cell-1-experience">951</div></td>
								<td data-field="score"><div
										class="layui-table-cell laytable-cell-1-score">14</div></td>
								<td data-field="classify"><div
										class="layui-table-cell laytable-cell-1-classify">词人</div></td>
								<td data-field="wealth"><div
										class="layui-table-cell laytable-cell-1-wealth">16503371</div></td>
							</tr>
							<tr data-index="9">
								<td data-field="id"><div
										class="layui-table-cell laytable-cell-1-id">10009</div></td>
								<td data-field="username"><div
										class="layui-table-cell laytable-cell-1-username">user-9</div></td>
								<td data-field="sex"><div
										class="layui-table-cell laytable-cell-1-sex">女</div></td>
								<td data-field="city"><div
										class="layui-table-cell laytable-cell-1-city">城市-9</div></td>
								<td data-field="sign"><div
										class="layui-table-cell laytable-cell-1-sign">签名-9</div></td>
								<td data-field="experience"><div
										class="layui-table-cell laytable-cell-1-experience">484</div></td>
								<td data-field="score"><div
										class="layui-table-cell laytable-cell-1-score">75</div></td>
								<td data-field="classify"><div
										class="layui-table-cell laytable-cell-1-classify">词人</div></td>
								<td data-field="wealth"><div
										class="layui-table-cell laytable-cell-1-wealth">86801934</div></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="layui-table-fixed layui-table-fixed-l">
					<div class="layui-table-header">
						<table class="layui-table" border="0" cellspacing="0"
							cellpadding="0">
							<thead>
								<tr>
									<th data-field="id"><div
											class="layui-table-cell laytable-cell-1-id">
											<span>ID</span><span class="layui-table-sort layui-inline"><i
												class="layui-edge layui-table-sort-asc"></i><i
												class="layui-edge layui-table-sort-desc"></i></span>
										</div></th>
								</tr>
							</thead>
						</table>
					</div>
					<div class="layui-table-body" style="height: 235px;">
						<table class="layui-table" border="0" cellspacing="0"
							cellpadding="0">
							<tbody>
								<tr data-index="0" class="">
									<td data-field="id"><div
											class="layui-table-cell laytable-cell-1-id">10000</div></td>
								</tr>
								<tr data-index="1" class="">
									<td data-field="id"><div
											class="layui-table-cell laytable-cell-1-id">10001</div></td>
								</tr>
								<tr data-index="2" class="">
									<td data-field="id"><div
											class="layui-table-cell laytable-cell-1-id">10002</div></td>
								</tr>
								<tr data-index="3" class="">
									<td data-field="id"><div
											class="layui-table-cell laytable-cell-1-id">10003</div></td>
								</tr>
								<tr data-index="4" class="">
									<td data-field="id"><div
											class="layui-table-cell laytable-cell-1-id">10004</div></td>
								</tr>
								<tr data-index="5" class="">
									<td data-field="id"><div
											class="layui-table-cell laytable-cell-1-id">10005</div></td>
								</tr>
								<tr data-index="6" class="">
									<td data-field="id"><div
											class="layui-table-cell laytable-cell-1-id">10006</div></td>
								</tr>
								<tr data-index="7">
									<td data-field="id"><div
											class="layui-table-cell laytable-cell-1-id">10007</div></td>
								</tr>
								<tr data-index="8">
									<td data-field="id"><div
											class="layui-table-cell laytable-cell-1-id">10008</div></td>
								</tr>
								<tr data-index="9">
									<td data-field="id"><div
											class="layui-table-cell laytable-cell-1-id">10009</div></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<style>
.laytable-cell-1-id {
	width: 80px;
}

.laytable-cell-1-username {
	width: 80px;
}

.laytable-cell-1-sex {
	width: 80px;
}

.laytable-cell-1-city {
	width: 80px;
}

.laytable-cell-1-sign {
	width: 219px;
}

.laytable-cell-1-experience {
	width: 80px;
}

.laytable-cell-1-score {
	width: 80px;
}

.laytable-cell-1-classify {
	width: 80px;
}

.laytable-cell-1-wealth {
	width: 120px;
}
</style>
		</div>
	</div>    
    
<script src="layui.js" charset="utf-8"></script>

</body>
</html>