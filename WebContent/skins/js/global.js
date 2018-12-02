var n = document,
i = function() {
    var e = n.scripts,
    t = e[e.length - 1].src;
    return t.substring(0, t.lastIndexOf("/skins/"))
};

var _curProject = i();
var _prefix = "common"