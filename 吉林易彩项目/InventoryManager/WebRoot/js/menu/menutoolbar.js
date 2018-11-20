/** 
 * 该扩展可以通过一定解析规则的json串生成菜单 
 * 通过itemclick事件调用点击菜单项的监听函数； 
 * 通过afterload事件调用菜单加载完毕后的监听函数； 
 * 注意：使用该控件须确保父菜单code值小于子菜单 
 * 'code', 'name', 'url', 'leaf', 'enabled', 'parentcode', 'iconcls' 
 *  code、parentcode都是编码，通常来讲这个菜单表都是开发人员手工写入数据库的 
 *  name 对应菜单上的文字，leaf为空字符串、false、0的时候表示该节点下面还有子节点 
 *  enabled 字段目前还没什么用，可以无视 
 *   
 *  iconcls 就是菜单文件前图标的css样式类 
 *   
 *  1.1修改：加载完成后清楚内置的父容器缓存 
 * 
 * @version 1.1 2014-1-15 
 * @author zhubl 
 */  
Ext.namespace("com.increase.cen");
  
com.increase.cen.MenuToolbar = Ext.extend(Ext.Toolbar, {  
    height: 25,  
    /** 
     * @cfg {root} store的root 
     */  
    root: 'menus',  
    /** 
     * @cfg {split} 一级菜单之间是否用横线间隔 
     */  
    split: true,  
    /** 
     * @cfg {store} 读取记录的store 默认为jsonstore 
     */  
    /** 
     * @cfg {url} 用于读取菜单记录串的url 
     */  
    url: 'main!getMenus.action',
    initComponent: function(){  
        this.addEvents(
        /** 
         * @event itemclick 菜单被点击时触发 
         */  
        'itemclick',          
        /** 
         * @event afterload 菜单项加载完毕后触发 
         */  
        'afterload');  
        if (!this.store)   
            this.store = new Ext.data.JsonStore({  
                url: this.url,  
                root: this.root,  
                fields: ['code', 'name', 'url', 'leaf', 'enabled', 'parentcode', 'iconcls']  
            });  
        this.store.on('load', this.loadRecords, this);  
        this.store.load();  
        com.increase.cen.MenuToolbar.superclass.initComponent.call(this);  
    },  
    //private 遍历records生成菜单  
    loadRecords: function(s, r, o){  
        this.menuCache = {};//对菜单(Menu)按照code进行缓存 code必须是唯一的！  
        this.store.sort('code');
        s.each(function(record){
            var parentCt = this.getParnetCt(record.get('parentcode'));  
            if (parentCt === this && this.split)   
                this.add('-');  
            if (!record.get('leaf')) {//是个菜单  
                var menu = new Ext.menu.Menu({  
                    code: record.get('code')
                });  
                parentCt.add({  
                    text: record.get('name'),  
                    iconCls: record.get('iconcls'),  
                    menu: menu, 
                    width: 100
                });  
                this.menuCache[record.get('code')] = menu;  
            }  else {  
                var item = new Ext.menu.Item({  
                    text: record.get('name'),  
                    code: record.get('code'),  
                    url: record.get('url')  
                });  
                item.on('click', function(item){//注册点击事件监听函数
                	reflash();
                    this.lastItem = item;  
                    this.fireEvent('itemclick', this, item);  
                    parent.Ext.getCmp("pnNorth").setTitle("&nbsp;网络资源清查工具管理系统&nbsp;&nbsp;&nbsp;&nbsp;-导航&gt;&gt;"+item.text);
                    parent.Ext.getCmp("pnCenterframe").body
							.update("<iframe id='domainTreeIframe' src='"+item.url+"' scrolling='yes' width='100%' height='100%' frameborder='no' border='0' marginwidth='0' marginheight='0' align='center'></iframe>");
                }, this);  
                parentCt.add(item);  
            }  
        }, this);  
        this.add('-');
        delete this.menuCache;//没用，不要了  
        this.doLayout();  
        this.fireEvent('afterload', this);  
    },  
    //private 根据parentcode获取它的容器对象,parentcode不存在或是cache中找不到就返回toolbar对象  
    getParnetCt: function(parentcode){  
        if (!parentcode) { //parentcode为空时父容器就是toolbar  
            return this;  
        }  
        return this.menuCache[parentcode] ? this.menuCache[parentcode] : this;  
    },  
    //根据item对象或者code属性获取它的的路径  
    getItemPath: function(item){  
        var code = typeof item == 'string' ? item : item.code;  
        var record = this.store.getAt(this.store.find('code', code));  
        if (!record)   
            return;  
        var path = [];         
        while (record) {  
            path.push(record.get('name'));  
            index = this.store.find('code', record.get('parentcode'));  
            record = this.store.getAt(index);              
        }  
        path.reverse();  
        return path.join('-->');  
    },  
    //获取最后一个被点击的item  
    getLastItem: function(){  
        return this.lastItem;  
    },  
    load: function(){  
        this.store.load();  
    }  
});
Ext.reg("MenuToolBar", com.increase.cen.MenuToolbar);