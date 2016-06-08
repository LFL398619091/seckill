// 存放主要的交互逻辑js
// javascript模块化编程
var seckill = {
    // 封装秒杀相关的ajax的地址
    URL: {
        now: function () {
            return '/seckill/seckill/time';
        },
        exposerUrl: function (seckillId) {
            return '/seckill/seckill/' + seckillId + '/exposer';
        },
        killUrl: function (seckillid, md5) {
            return '/seckill/seckill/' + seckillid + '/' + md5 + '/excute';
        }
    },
    //验证手机号
    validatePhone: function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },
    handleSeckill: function (seckillId, node) {
        //处理秒杀逻辑
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
        $.post(seckill.URL.exposerUrl(seckillId), {}, function (result) {
            if (result && result['success']) {
                var exposer = result['data'];
                //已经开启秒杀
                if (exposer['exposed']) {
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.killUrl(seckillId, md5);
                    //one=>绑定一次点击事件,点完之后该click失效
                    $('#killBtn').one('click', function () {
                        //执行秒杀请求的操作
                        //1)先禁用按钮
                        $(this).addClass('disabled');
                        //2)发送秒杀请求,执行秒杀
                        $.post(killUrl, {}, function (result) {
                            if (result && result['success']) {

                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                //显示秒杀结果
                                node.html('<span class="label label-success">' + stateInfo + '</span>');

                            } else {
                                //其他未知异常
                                node.html('<span class="label label-success">未知异常</span>');
                                setTimeout("", 2000);
                                window.location.reload();
                            }
                        });
                    });
                    node.show();
                } else {
                    //未开启秒杀(用户电脑计时比服务器快)
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    //重新倒计时
                    seckill.countdown(seckillId, now, start, end);
                }
            } else {
                console.log('result:' + result);
            }
        });
    },
    countdown: function (seckillId, nowTime, startTime, endTime) {
        console.log('开始倒计时');
        var seckillBox = $('#seckill-box');
        //时间判断
        if (nowTime > endTime) {
            seckillBox.html('秒杀结束');
        } else if (nowTime < startTime) {
            //秒杀未开始,计时事件绑定
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime, function (event) {
                var format = event.strftime('秒杀倒计时: %D天 %H时 %M分 %S秒');
                seckillBox.html(format);
            }).on('finish.countdown', function () {//倒计时结束,暴露秒杀接口给用户
                //获取秒杀地址,控制秒杀逻辑,执行秒杀
                seckill.handleSeckill(seckillId, seckillBox);
            });
        } else {
            //秒杀开始了
            seckill.handleSeckill(seckillId, seckillBox);
        }
    },
    // 详情页m秒杀逻辑
    detail: {
        //详情页初始化
        init: function (params) {
            // 用户手机验证和登录,计时交互
            //规划我们的交互流程
            var killPhone = $.cookie('killPhone')//在cookie中查找手机号

            //验证手机号
            if (!seckill.validatePhone(killPhone)) {//未登录
                //绑定手机号
                var killPhoneModal = $('#killPhoneModal');
                //显示弹出层
                killPhoneModal.modal({
                    show: true,//显示弹出层
                    backdrop: 'static',//禁止位置关闭
                    keyboard: false,//关闭键盘事件
                });
                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhone').val();
                    if (seckill.validatePhone(inputPhone)) {
                        //将手机号写入cookie
                        $.cookie('killPhone', inputPhone, {expires: 7, path: '/seckill'});
                        //验证通过刷新页面
                        window.location.reload();
                    } else {
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误!</label>').show(300);
                    }
                });
            }
            //已经登录
            // 1)计时交互
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            $.post(seckill.URL.now(), {}, function (result) {
                if (result && result['success']) {
                    var nowTime = result['data'];
                    //时间判断,计时交互
                    seckill.countdown(seckillId, nowTime, startTime, endTime);

                } else {
                    console.log('result:' + result);
                }

            });

        }
    }
}