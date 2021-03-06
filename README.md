# 手机号码绑定查询
## 说明
目前各大互联网站注册账号均要求绑定手机号码。导致消费者更换号码不清楚自己需要解绑多少账号。本app利用各大网站的注册检测手机号码是否被绑定的api，批量查询某手机号码绑定的账号

## 配置文件说明
文件位置：`./requireUrls.json`
```jsom
{
  "version": "配置文件版本，同时更改./version.json",
  "requireUrls": [
    {
      "name": "必填，该网站网站名",
      "disable":"true", //true|false，表示这个网站，本app是否不支持，当为true，仅需要填5项内容
      "disableDescription":"需要输入验证码", //这个网站不支持的原因
      "url":"必填，该网站请求手机绑定信息查询的接口",
      "loginUrl":"必填，登录该网站页面",
      "logoUrl":"必填，该网站logo图片一般为：主页/favicon.ico",
      "registerUrl":"必填，注册页面Url",
      //以下为对url参数的配置
      "headers":{}, //选填，某些api需要配置一些请求头（不要填Cookie）
      "params":{}, //选填，，某些api需要的固定参数，不包括手机号参数,若是get方式，这些参数可以放置到url上，也可以混用，get方式注意参数顺序
      "method":"get", //选填，默认为post，仅支持get、post
      "phoneKey":"phone", //必填，手机参数的key
      "phonePosition":0, //选填，手机参数在的位置，防止后台对get url进行强正则校验（参数顺序），默认在最后
      "timestampKey":"" //选填，存在时间戳时需要填写，否则不填此字段
      "timestampUnit":"ms" //选填，存在时间戳时需要填写，表示时间戳单位，选填"s"和"ms"，表示秒和毫秒，默认为毫秒
      "bound": "手机号已被注册", //必填，请求接口返回的值的包含此内容表示，该手机号已被绑定
      "noBind": "成功", //必填，请求接口返回的值的包含此内容表示，该手机号已被绑定
      "author":"Rectcircle" //选填，默认为anonymous，分析撰写该配置的作者，将显示到app上
    },//get方式例子 
    {
      "name": "百度",
      "url":"https://passport.baidu.com/v2/",
      "loginUrl":"https://passport.baidu.com",
      "logoUrl":"https://www.baidu.com/favicon.ico",
      "registerUrl":"https://passport.baidu.com/v2/?reg",
      "headers":{},
      "params":{
        "regphonecheck":"",
        "apiver":"v3"
      },
      "method":"get",
      "phoneKey":"phone",
      "bound": "手机号已被注册",
      "noBind": "成功",
      "author":"Rectcircle"
    },
    //post方式例子如下
    {
      "name": "新浪",
      "url":"https://login.sina.com.cn/signup/check_user.php",
      "loginUrl":"https://login.sina.com.cn/signup/signin.php",
      "logoUrl":"http://www.sina.com.cn/favicon.ico",
      "registerUrl":"https://login.sina.com.cn/signup/signup?entry=homepage",
      "params":{
        "from":"mobile",
        "format":"json"
      },
      "method":"post",
      "phoneKey":"name",
      "bound": "100001",
      "noBind": "100000",
      "author":"Rectcircle"
    },
    { //网站本app不支持的情况
      "name": "淘宝",
      "disable":"true",
      "disableDescription":"未找到相关接口",
      "loginUrl":"https://login.taobao.com/member/login.jhtml",
      "logoUrl":"https://www.taobao.com/favicon.ico",
      "author":"Rectcircle"
    }
  ]
}
```

