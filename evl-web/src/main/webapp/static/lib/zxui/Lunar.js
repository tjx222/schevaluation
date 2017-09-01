define("zxui/Lunar", ["require", "./lib", "./Control"], 
function(require) {
    function e(e) {
        var i = 348 + (g[e - 1900] >> 4).toString(2).replace(/0/g, "").length;
        return i + t(e)
    }
    function t(e) {
        return i(e) ? 15 === (15 & g[e - 1899]) ? 30: 29: 0
    }
    function i(e) {
        var t = 15 & g[e - 1900];
        return 15 === t ? 0: t
    }
    function n(e, t) {
        return g[e - 1900] & 65536 >> t ? 30: 29
    }
    function r(e, t) {
        var i = new Date(31556925974.7 * (e - 1900) + 6e4 * y[t] + Date.UTC(1900, 0, 6, 2, 5));
        return i.getUTCDate() + (v[e + "" + t] || 0)
    }
    function o(e) {
        var t = e.getFullYear(),
        i = e.getMonth(),
        n = e.getDate(),
        o = n === r(t, 2 * i) ? m[2 * i] : n === r(t, 2 * i + 1) ? m[2 * i + 1] : "";
        return o ? {
            type: "solar-term",
            text: o
        }: null
    }
    function a(e) {
        if (11 === e.month && e.day > 28) {
            var t = new Date(e.solar.getTime() + 864e5);
            if (t = u(t), 1 === t.day) e.month = 0,
            e.day = 0
        }
        var i = e.leap ? "": b[f(e.month + 1) + f(e.day)] || "";
        return i ? {
            type: "lunar-festival",
            text: i
        }: null
    }
    function s(e) {
        var t = _[f(e.getMonth() + 1) + f(e.getDate())];
        return t ? {
            type: "solar-festival",
            text: t
        }: null
    }
    function l(e) {
        var t = e.getDay(),
        i = [f(e.getMonth() + 1), t],
        n = e.getDate(),
        r = (7 + t - (n - 1)) % 7;
        r = 0 > r ? r + 7: r;
        var o = new Date(e.getFullYear(), i[0], 0),
        a = o.getDay(),
        s = o.getDate(),
        l = Math.floor((n + r - 1) / 7) + (r > t ? 0: 1),
        h = 4 + Math.floor((t + s - n) / 7) + (t > a ? 0: 1),
        u = x[i.join(l)] || x[i.join(h)];
        return u ? {
            type: "solar-weak-festival",
            text: u
        }: null
    }
    function h(e, t) {
        return a(t) || s(e) || l(e) || o(e)
    }
    function u(r) {
        var o,
        a = 0,
        s = 0,
        l = (Date.UTC(r.getFullYear(), r.getMonth(), r.getDate()) - Date.UTC(1900, 0, 31)) / 864e5;
        for (o = 1900; 2100 > o && l > 0; o++) s = e(o),
        l -= s;
        if (0 > l) l += s,
        o--;
        var h = o;
        a = i(o);
        var u = !1;
        for (o = 1; 13 > o && l > 0; o++) {
            if (a > 0 && o === a + 1 && !u)--o,
            u = !0,
            s = t(h);
            else s = n(h, o);
            if (u && o === a + 1) u = !1;
            l -= s
        }
        if (0 === l && a > 0 && o === a + 1) if (u) u = !1;
        else u = !0,
        --o;
        if (0 > l) l += s,
        --o;
        return {
            year: h,
            month: o - 1,
            day: l + 1,
            leap: u,
            solar: r
        }
    }
    function c(e, t) {
        var i,
        n = u(e),
        r = n.month,
        o = n.day,
        a = ["初", "十", "廿", "卅", "卌"],
        s = ["日", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"],
        l = ["正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "腊"],
        c = h(e, n);
        if (!c) {
            if (1 === o) i = (n.leap ? "闰": "") + l[r] + "月";
            else if (o % 10 > 0) i = a[o / 10 | 0] + s[o % 10];
            else i = (o > 10 ? s[o / 10] : a[0]) + s[10];
            c = {
                type: "lunar",
                text: i
            }
        }
        return '<span class="' + t + "-" + c.type + '" title="'+c.text+'">' + c.text + "</span>"
    }
    function f(e) {
        return (e > 9 ? "": "0") + e
    }
    var d = require("./lib"),
    p = require("./Control"),
    g = [19416, 19168, 42352, 21717, 53856, 55632, 21844, 22191, 39632, 21970, 19168, 42422, 42192, 53840, 53909, 46415, 54944, 44450, 38320, 18807, 18815, 42160, 46261, 27216, 27968, 43860, 11119, 38256, 21234, 18800, 25958, 54432, 59984, 27285, 23263, 11104, 34531, 37615, 51415, 51551, 54432, 55462, 46431, 22176, 42420, 9695, 37584, 53938, 43344, 46423, 27808, 46416, 21333, 19887, 42416, 17779, 21183, 43432, 59728, 27296, 44710, 43856, 19296, 43748, 42352, 21088, 62051, 55632, 23383, 22176, 38608, 19925, 19152, 42192, 54484, 53840, 54616, 46400, 46752, 38310, 38335, 18864, 43380, 42160, 45690, 27216, 27968, 44870, 43872, 38256, 19189, 18800, 25776, 29859, 59984, 27480, 23232, 43872, 38613, 37600, 51552, 55636, 54432, 55888, 30034, 22176, 43959, 9680, 37584, 51893, 43344, 46240, 47780, 44368, 21977, 19360, 42416, 20854, 21183, 43312, 31060, 27296, 44368, 23378, 19296, 42726, 42208, 53856, 60005, 54576, 23200, 30371, 38608, 19195, 19152, 42192, 53430, 53855, 54560, 56645, 46496, 22224, 21938, 18864, 42359, 42160, 43600, 45653, 27951, 44448, 19299, 37759, 18936, 18800, 25776, 26790, 59999, 27424, 42692, 43759, 37600, 53987, 51552, 54615, 54432, 55888, 23893, 22176, 42704, 21972, 21200, 43448, 43344, 46240, 46758, 44368, 21920, 43940, 42416, 21168, 45683, 26928, 29495, 27296, 44368, 19285, 19311, 42352, 21732, 53856, 59752, 54560, 55968, 27302, 22239, 19168, 43476, 42192, 53584, 62034, 54560],
    m = ["小寒", "大寒", "立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至", "小暑", "大暑", "立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至"],
    y = [0, 21208, 42467, 63836, 85337, 107014, 128867, 150921, 173149, 195551, 218072, 240693, 263343, 285989, 308563, 331033, 353350, 375494, 397447, 419210, 440795, 462224, 483532, 504758],
    v = {
        19762: 1,
        19802: 1,
        20092: 1,
        20129: -1,
        201222: 1,
        20132: 1,
        201313: -1,
        201323: 1,
        20144: 1,
        20150: 1,
        201622: 1,
        201713: -1,
        201723: -1,
        20183: 1,
        20185: 1,
        20192: -1,
        201911: -1,
        202012: -1,
        202015: -1,
        202022: 1
    },
    b = {
        "0100": "除夕",
        "0101": "春节",
        "0115": "元宵节",
        "0202": "龙抬头",
        "0505": "端午节",
        "0707": "七夕",
        "0715": "中元节",
        "0815": "中秋节",
        "0909": "重阳节",
        1208: "腊八节",
        1223: "小年"
    },
    _ = {
        "0101": "元旦",
        "0202": "世界湿地日",
        "0210": "国际气象节",
        "0214": "情人节",
        "0301": "国际海豹日",
        "0303": "全国爱耳日",
        "0308": "妇女节",
        "0312": "植树节",
        "0315": "消费者权益日",
        "0317": "中国国医节",
        "0321": "世界森林日",
        "0322": "世界水日",
        "0323": "国际气象日",
        "0324": "世界防治结核病日",
        "0401": "愚人节",
        "0407": "世界卫生日",
        "0422": "地球日",
        "0423": "世界读书日",
        "0424": "亚非新闻工作者节",
        "0501": "劳动节",
        "0504": "青年节",
        "0508": "世界红十字日",
        "0512": "国际护士节",
        "0515": "国际家庭日",
        "0517": "国际电信日",
        "0518": "国际博物馆日",
        "0520": "全国学生营养日",
        "0531": "无烟日",
        "0601": "儿童节",
        "0605": "世界环境日",
        "0606": "爱眼日",
        "0617": "世界防治荒漠化和干旱日",
        "0623": "国际奥林匹克日",
        "0625": "全国土地日",
        "0626": "国际禁毒日",
        "0701": "建党日",
        "0702": "国际体育记者日",
        "0707": "抗战纪念日",
        "0711": "世界人口日",
        "0801": "建军节",
        "0808": "中国男子节",
        "0903": "抗日战争胜利纪念日",
        "0908": "国际扫盲日",
        "0910": "教师节",
        "0916": "国际臭氧层保护日",
        "0918": "九·一八",
        "0920": "国际爱牙日",
        "0921": "国际和平日",
        1001: "国庆节",
        1004: "世界动物日",
        1008: "全国高血压日",
        1009: "世界邮政日",
        1010: "辛亥革命纪念日",
        1013: "世界保健日",
        1014: "世界标准日",
        1015: "国际盲人节",
        1016: "世界粮食日",
        1017: "国际消除贫困日",
        1022: "世界传统医药日",
        1024: "联合国日",
        1031: "万圣节",
        1107: "十月革命纪念日",
        1108: "中国记者日",
        1109: "全国消防日",
        1110: "世界青年节",
        1111: "光棍节",
        1112: "孙中山诞辰纪念日",
        1114: "联合国糖尿病日",
        1117: "国际大学生节",
        1201: "艾滋病日",
        1203: "国际残疾人日",
        1205: "国际志愿人员日",
        1210: "世界人权日",
        1212: "西安事变纪念日",
        1213: "南京大屠杀",
        1220: "澳门回归日",
        1220: "澳门回归日",
        1221: "国际篮球日",
        1224: "平安夜",
        1225: "圣诞节"
    },
    x = {
        "0150": "国际麻风节",
        "0520": "母亲节",
        "0532": "国际牛奶节",
        "0630": "父亲节",
        "0730": "被奴役国家周",
        "0930": "世界清洁地球日",
        1011: "世界住房日",
        1023: "国际减轻自然灾害日",
        1144: "感恩节"
    },
    w = "yyyy-MM-dd",
    S = {},
    T = p.extend({
        type: "Lunar",
        options: {
            main: "",
            prefix: "ecl-ui-lunar",
            dateFormat: "",
            dateClassName: "",
            range: {
                begin: "1900-01-01",
                end: "2100-12-31"
            },
            value: "",
            process: null,
            first: 0,
            lang: {
                week: "周",
                days: "日,一,二,三,四,五,六"
            }
        },
        binds: "onClick",
        init: function(e) {
            this.dateFormat = e.dateFormat || T.DATE_FORMAT || w,
            this.days = e.lang.days.split(","),
            this.date = this.from(e.value),
            this.value = this.format(this.date),
            this.setRange(e.range || T.RANGE)
        },
        from: function(e, t) {
            if (t = t || this.dateFormat, d.isString(e)) {
                if (!e) return new Date;
                t = t.split(/[^yMdW]+/i),
                e = e.split(/\D+/);
                for (var i = {},
                n = 0, r = t.length; r > n; n++) if (t[n] && e[n] && (t[n].length > 1 && e[n].length === t[n].length || 1 === t[n].length)) i[t[n].toLowerCase()] = e[n];
                var o = i.yyyy || i.y || (i.yy < 50 ? "20": "19") + i.yy,
                a = 0 | (i.m || i.mm),
                s = 0 | (i.d || i.dd);
                return new Date(0 | o, a - 1, s)
            }
            return e
        },
        format: function(e, t) {
            if (t = (t || this.dateFormat).toLowerCase(), d.isString(e)) e = this.from(e);
            var i = this.options,
            n = i.first,
            r = e.getFullYear(),
            o = e.getMonth() + 1,
            a = e.getDate(),
            s = e.getDay();
            if (n) s = (s - 1 + 7) % 7;
            s = this.days[s];
            var l = {
                yyyy: r,
                yy: r % 100,
                y: r,
                mm: f(o),
                m: o,
                dd: f(a),
                d: a,
                w: s,
                ww: i.lang.week + s
            };
            return t.replace(/y+|M+|d+|W+/gi, 
            function(e) {
                return l[e] || ""
            })
        },
        getYYYYMM: function(e) {
            return d.isString(e) ? e: this.format(this.from(e), "yyyyMM")
        },
        render: function() {
            var e = this.options;
            if (!this.rendered) {
                this.rendered = !0;
                var t = this.main = d.g(e.main),
                i = e.prefix,
                n = i + "-month";
                t.innerHTML = '<div class="' + n + '"></div><a href="#" class="' + i + '-pre"></a><a href="#" class="' + i + '-next"></a><a href="#" class="' + i + '-go-today">今天</a><a href="#" class="'+i+'-add-event">新建日程</a>',
                this.monthElement = d.q(n, t)[0],
                this.build(),
                d.on(t, "click", this.onClick),
                d.addClass(t, "c-clearfix")
            }
            return this
        },
        build: function(e) {
            e = new Date((e || this.date).getTime()),
            this.monthElement.innerHTML = this.buildMonth(e),
            this.updateStatus(),
            this.updatePrevNextStatus(e),
            this.fire("navigate", {
                date: e,
                yyyyMM: this.getYYYYMM(e)
            })
        },
        updatePrevNextStatus: function(e) {
            var t = this.options,
            i = t.prefix,
            n = this.range,
            r = d.q(i + "-pre", this.main)[0],
            o = d.q(i + "-next", this.main)[0];
            e = e || this.date || this.from(this.value);
            var a = this.getYYYYMM(e);
            if (r) d[!n || !n.begin || this.getYYYYMM(n.begin) < a ? "show": "hide"](r);
            if (o) d[!n || !n.end || this.getYYYYMM(n.end) > a ? "show": "hide"](o)
        },
        buildMonth: function(e) {
            var t = e.getFullYear(),
            i = e.getMonth() + 1,
            n = e.getDate(),
            r = e.getDay(),
            o = t + f(i);
            if (S[o]) return S[o];
            var a = 7,
            s = "-",
            l = this.options,
            h = l.prefix,
            u = [];
            u.push("<h3>" + t + "年" + i + "月</h3>");
            var d,
            p,
            g,
            m = l.first,
            y = this.days;
            for (u.push('<ul class="c-clearfix">'), d = 0, p = y.length; p > d; d++) g = d === a - 1 || m && d === a - 2 || !m && d === m ? ' class="' + h + '-weekend"': "",
            u.push("<li" + g + ">" + y[d] + "</li>");
            u.push("</ul>"),
            u.push('<p class="c-clearfix">');
            var v,
            b,
            _,
            x,
            w = 0,
            T = (a + r + 1 - n % a) % a;
            if (p = T - m, p > 0) {
                for (e.setDate(0), v = e.getFullYear(), b = e.getMonth() + 1, _ = e.getDate(), x = [v, f(b), ""].join(s), g = h + "-pre-month", d = _ - p + 1; _ >= d; d++) w %= a,
                e.setDate(d),
                u.push('<a href="#" hidefocus class="' + g + '" data-date="' + x + f(d) + '" data-week="' + w + '">' + d + " " + c(e, h) + "</a>"),
                w++;
                e.setDate(_ + 1)
            }
            for (e.setDate(1), e.setMonth(i), e.setDate(0), x = [t, f(i), ""].join(s), d = 1, p = e.getDate(); p >= d; d++) w %= a,
            e.setDate(d),
            u.push('<a href="#" hidefocus  data-date="' + x + f(d) + '" data-week="' + w + '">' + d + " " + c(e, h) + "</a>"),
            w++;
            for (e.setDate(p + 1), v = e.getFullYear(), b = e.getMonth() + 1, x = [v, f(b), ""].join(s), g = h + "-next-month", p = (p + Math.max(0, T - m)) % 7, p = p > 0 ? 7 - p: 0, d = 1; p >= d; d++) w %= a,
            e.setDate(d),
            u.push('<a href="#" hidefocus class="' + g + '" data-date="' + x + f(d) + '" data-week="' + w + '">' + d + " " + c(e, h) + "</a>"),
            w++;
            return u.push("</p>"),
            S[o] = u.join(""),
            S[o]
        },
        onClick: function(e) {
            var t = e;
            if (t) {
                for (var i = d.getTarget(t), n = i.tagName;
                "A" !== n && i !== this.main;) i = i.parentNode,
                n = i.tagName;
                switch (n) {
                case "A":
                    d.preventDefault(t);
                    var r = this.options.prefix,
                    o = r + "-pre",
                    a = r + "-next",
                    s = r + "-go-today",
                    l = r + "-add-event",
                    h = r + "-disabled",
                    u = d.hasClass,
                    c = d.stopPropagation;
                    if (u(i, o)) this.showPreMonth(),
                    c(t);
                    else if (u(i, a)) this.showNextMonth(),
                    c(t);
                    else if (u(i, s)) {
                        var f = new Date;
                        if (this.getYYYYMM(this.date) !== this.getYYYYMM(f)) this.date = f,
                        this.build()
                    } else if (u(i, l)) this.fire("add", t);
                    else if (!u(i, h)) this.pick(i, t)
                }
                this.fire("click", e)
            }
        },
        showPreMonth: function() {
            var e = this.date;
            e.setDate(0),
            this.build(e)
        },
        showNextMonth: function() {
            var e = this.date;
            e.setDate(1),
            e.setMonth(e.getMonth() + 1),
            this.build(e)
        },
        updateStatus: function() {
            var e = this.options,
            t = e.prefix,
            i = e.process,
            n = e.first,
            r = e.dateClassName,
            o = new Date,
            a = this.format(o, w),
            s = this.range,
            l = "",
            h = "9999-12-31";
            if (s) l = s.begin && this.format(s.begin, w) || l,
            h = s.end && this.format(s.end, w) || h;
            var u,
            c,
            f,
            d,
            p,
            g,
            m,
            y,
            v,
            b = t + "-pre-month",
            _ = t + "-next-month",
            x = t + "-disabled",
            S = t + "-today",
            T = t + "-weekend",
            C = this.main.getElementsByTagName("p");
            for (u = 0, c = C.length; c > u; u++) for (p = C[u].getElementsByTagName("a"), f = 0; d = p[f]; f++) {
                if (g = [], m = d.getAttribute("data-date"), y = d.className, v = !0, s && (l > m || m > h)) g.push(x),
                v = !1;
                var E = f % 7;
                if (6 === E || n && 5 === E || !n && 0 === E) g.push(T);
                if (~y.indexOf(b)) g.push(b);
                else if (~y.indexOf(_)) g.push(_);
                else if (m === a) g.push(S);
                if (r) g.push(r);
                if (i) i.call(this, d, g, m, v);
                d.className = g.join(" ")
            }
        },
        pick: function(e, t) {
            var i = e.getAttribute("data-week"),
            n = this.from(e.getAttribute("data-date"), w),
            r = this.format(n);
            this.fire("pick", {
                value: r,
                week: this.options.lang.week + this.days[i],
                date: n,
                event: t
            })
        },
        getDayElements: function() {
            return this.monthElement.getElementsByTagName("a")
        },
        getDaysInfo: function() {
            var e = this.getDayElements(),
            t = {};
            return d.each(e, 
            function(e, i) {
                var n = e.getAttribute("data-date");
                t[n] = {
                    date: n,
                    element: e,
                    week: 0 | e.getAttribute("data-week"),
                    index: i,
                    rows: i / 7 | 0,
                    cols: i % 7
                }
            }),
            t
        },
        setRange: function(e) {
            if (e) {
                var t = e.begin,
                i = e.end;
                if (t && d.isString(t)) e.begin = this.from(t);
                if (i && d.isString(i)) e.end = this.from(i);
                this.range = e,
                this.rendered && this.updatePrevNextStatus()
            }
        },
        setValue: function(e) {
            this.date = this.from(e),
            this.value = this.format(this.date),
            this.build()
        }
    });
    return T.DATE_FORMAT = w,
    T
});