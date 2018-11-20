/**
 * 画出节点
 * @param x
 * @param y
 * @param w
 * @param h
 * @param text
 * @param path
 * @returns {___anonymous195_198}
 */
function newNode(x, y, w, h, text, path) {
	var node = new JTopo.Node(text);
	if (null != path) {
		node.setImage(path, false);
	}
	node.setLocation(x, y);
	node.setSize(w, h);
	node.fontColor = '155,123,2';
	node.font = 'bold 16px 微软雅黑';
	//scene.add(node);
	return node;
}

/**
 * 画出节点
 * @param x
 * @param y
 * @param w
 * @param h
 * @param text
 * @param path
 * @returns {___anonymous195_198}
 */
function newNodeId(x, y, w, h, text, path,id) {
	var node = new JTopo.Node(text);
	if (null != path) {
		node.setImage(path, false);
	}
	node.setLocation(x, y);
	node.setSize(w, h);
	node.id=id;
	node.fontColor = '155,123,2';
	node.font = 'bold 16px 微软雅黑';
	//scene.add(node);
	return node;
}
/**
 * 两个点进行连线
 * @param nodeA
 * @param nodeZ
 * @param text
 * @param dashedPattern
 * @returns {___anonymous538_541}
 */
function newLink(nodeA, nodeZ, text, dashedPattern) {
	var link = new JTopo.Link(nodeA, nodeZ, text);
	link.lineWidth = 3; // 线宽
	// link.dashedPattern = dashedPattern; // 虚线
	link.bundleOffset = 60; // 折线拐角处的长度
	link.bundleGap = 20; // 线条之间的间隔
	link.textOffsetY = 3; // 文本偏移量（向下3个像素）
	link.strokeColor = '0,200,255';
	//scene.add(link);
	return link;
}


/**
 * 折线
 * @param nodeA
 * @param nodeZ
 * @param text
 * @param direction
 * @param dashedPattern
 * @returns {___anonymous538_541}
 */
function newFoldLink(nodeA, nodeZ, text, direction, dashedPattern){
	var link = new JTopo.FoldLink(nodeA, nodeZ, text);
	link.direction = direction || 'horizontal';
	link.arrowsRadius = 15; //箭头大小
	link.lineWidth = 3; // 线宽
	link.bundleOffset = 60; // 折线拐角处的长度
	link.bundleGap = 20; // 线条之间的间隔
	link.textOffsetY = 3; // 文本偏移量（向下3个像素）
	//link.strokeColor = JTopo.util.randomColor(); // 线条颜色随机
	//link.dashedPattern = dashedPattern;
	//scene.add(link);
	return link;
}

/**
 * 画出折线
 * @param nodeA
 * @param nodeZ
 * @param text
 * @param direction
 * @param dashedPattern
 * @returns {___anonymous1551_1554}
 */
function newFlexionalLink(nodeA, nodeZ, text, direction, dashedPattern) {
	var link = new JTopo.FlexionalLink(nodeA, nodeZ, text);
	link.direction = direction || 'horizontal';
	link.arrowsRadius = 10;
	link.lineWidth = 3; // 线宽
	link.offsetGap = 35;
	link.bundleGap = 15; // 线条之间的间隔
	link.textOffsetY = 10; // 文本偏移量（向下15个像素）
	link.strokeColor = '0,250,0';
	// link.dashedPattern = dashedPattern;
	//scene.add(link);
	return link;
}
/**
 * 画出曲线
 * @param nodeA
 * @param nodeZ
 * @param text
 * @returns {___anonymous2133_2136}
 */
function newCurveLink(nodeA, nodeZ, text) {
	var link = new JTopo.CurveLink(nodeA, nodeZ, text);
	link.lineWidth = 3; // 线宽
	//scene.add(link);
	return link;
}