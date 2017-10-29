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
      "url":"必填，该网站请求手机绑定信息查询的接口",
      "loginUrl":"必填，登录该网站页面",
      "logoUrl":"必填，该网站logo图片一般为：主页/favicon.ico",
      "cookieUrl":"选填，获取cookie的地址，一般为注册页面Url",
      //以下为对url参数的配置
      "headers":{}, //选填，某些api需要配置一些请求头（不要填Cookie）
      "params":{}, //post方式选填，get不填，某些api需要post需要的固定参数，不包括手机号参数
      "method":"get", //选填，默认为post，仅支持get、post
      "phoneKey":"phone", //必填，手机参数的key
      "prefix": "{{", //get方式选填，post方式不填，手机需要替换变量的名字
      "suffix": "}}", //类似上
      "bound": "手机号已被注册", //请求接口返回的值的包含此内容表示，该手机号已被绑定
      "noBind": "成功", //请求接口返回的值的包含此内容表示，该手机号已被绑定
      "author":"Rectcircle" //给配置分析撰写的作者，将显示到app上
    }, //例子如下
    {
      "name": "新浪",
      "url":"https://login.sina.com.cn/signup/check_user.php",
      "loginUrl":"https://login.sina.com.cn/signup/signin.php",
      "logoUrl":"http://www.sina.com.cn/favicon.ico",
      "cookieUrl":"https://login.sina.com.cn/signup/signup?entry=homepage",
      "headers":{
        "Referer":"https://login.sina.com.cn/signup/signup?entry=homepage"
      },
      "params":{
        "from":"mobile",
        "format":"json"
      },
      "method":"post",
      "phoneKey":"name",
      "bound": "100001",
      "noBind": "100000",
      "author":"Rectcircle"
    }
  ]
}
```

