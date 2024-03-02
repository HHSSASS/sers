<template>
    <div class="row">
        <div class="col-2"></div>
        <div class="col-8">
            <div style="font-size: 50px;">“菌”探卫士</div>
            <div>基于SERS复合基底的智能水体抗生素监测芯片</div>
            <div class="row">
                <div class="card border-dark" style="width: 17rem;margin-right: 10px;text-align: center;">
                    <img src="@/assets/images/1.0.png" class="card-img-top" alt="">
                    <div class="card-body">
                        <h5 class="card-title">Gps1.0</h5>
                        <p class="card-text">￥166</p>
                        <a class="btn btn-primary" data-bs-toggle="offcanvas" href="#offcanvasExample" role="button" aria-controls="offcanvasExample">产品详情</a>
                        <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasExample" aria-labelledby="offcanvasExampleLabel" style="width: 600px;text-align: left;">
                            <div class="offcanvas-header" style="border-bottom: 1px solid black;">
                                <h5 class="offcanvas-title" id="offcanvasExampleLabel">产品详情</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                            </div>
                            <div class="offcanvas-body" style="background-color: rgb(230, 230, 230);">
                                <div style="background-color: white;border-radius: 8px;">
                                    <div class="item">产品名称：</div>
                                    <div class="item">产品简介：</div>
                                </div>
                            </div>
                            <div style="border-top: 1px solid black;">
                                <div>订单详情</div>
                                <div>用户名：{{ $store.state.user.username }}</div>
                                <div>购买数量：</div>
                                <div>支付方式：</div>
                                <div style="display: flex;">
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault1" style="margin-top: 25px;">
                                        <label class="form-check-label" for="flexRadioDefault1">
                                            <div class="iconfont icon-weixinzhifu" style="font-size: 50px;"></div>
                                        </label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault2" checked>
                                        <label class="form-check-label" for="flexRadioDefault2">
                                            <div class="iconfont icon-alipay" style="font-size: 50px;"></div>
                                        </label>
                                    </div>
                                </div>
                                <div  style="display: flex; justify-content: flex-end;align-items: flex-end;">
                                    支付金额：
                                    <button type="button" class="btn btn-primary" style="border-radius: 0px;">立即购买</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card border-dark" style="width: 17rem;margin-right: 10px;text-align: center;">
                    <img src="@/assets/images/2.0.png" class="card-img-top" alt="">
                    <div class="card-body">
                        <h5 class="card-title">Gps2.0</h5>
                        <p class="card-text">￥288</p>
                        <a href="#" class="btn btn-primary">立即购买</a>
                    </div>
                </div>
                <div class="card border-dark" style="width: 17rem;margin-right: 10px;text-align: center;">
                    <img src="@/assets/images/3.0.png" class="card-img-top" alt="">
                    <div class="card-body">
                        <h5 class="card-title">Gps3.0</h5>
                        <p class="card-text">￥366</p>
                        <a href="#" class="btn btn-primary">立即购买</a>
                    </div>
                </div>
                <div class="card border-dark" style="width: 17rem;text-align: center;">
                    <img src="@/assets/images/camera.jpg" class="card-img-top" alt="">
                    <div class="card-body">
                        <h5 class="card-title">远程摄像头</h5>
                        <p class="card-text">￥40</p>
                        <a href="#" class="btn btn-primary">立即购买</a>
                    </div>
                </div>
            </div>
            <div style="font-size: 30px;">产品介绍</div>
            <div>“菌”探卫士采用自清洁SERS复合基底，结合纳米技术和光学等原理，能够将目标分子的拉曼信号放大106~1014倍，实现对水体中微量有害物质的高效捕获和灵敏检测。同时，通过微流控SERS芯片集成技术，实现样品的自动输送和处理，简化了检测流程，提高了检测效率和准确性。</div>
            <img src="@/assets/images/description.png" alt="">
            <div style="font-size: 30px;">产品优势</div>
            <div>高灵敏度：利用SERS技术，能够实现对水体中微量抗生素的精准检测，达到单个分子水平的检测能力。</div>
            <div>自动化检测：采用微流控SERS芯片集成技术，实现样品的自动输送和处理，简化操作流程，提高检测效率。</div>
            <div>高稳定性和耐用性：复合基底具备出色的稳定性和耐用性，能够在长期使用中保持稳定的检测性能。</div>
            <div>多功能应用：适用于环境监测、传染病预防与控制、水产养殖、农业灌溉水、医疗废水处理、饮用水安全等多个领域。</div>
            <div style="font-size: 30px;">产品用法</div>
            <div>样品处理：将水样取样后直接加载到芯片中。</div>
            <div>数据传输：通过与互联网连接，实现数据实时传输至云端服务器。</div>
            <div>数据分析：利用云端数据分析和可视化工具，直观展示监测结果。</div>
            <div>应用场景定制：根据客户需求，提供定制化的检测方案和技术支持。</div>
            <div style="font-size: 30px;">配套服务</div>
            <div>(1)产品培训与指导:为客户提供详细的产品操作培训，确保客户能够熟练、正确地使用产品。</div>
            <div>(2)技术支持与咨询:设立专业的技术支持团队，随时解答客户在使用过程中遇到的问题，提供及时、有效的解决方案</div>
            <div>(3)定制化检测方案:根据客户的具体需求，提供定制化的检测方案，包括检测项目、检测频率、数据报告等</div>
            <div>(4)数据分析与解读:对检测结果进行深入的数据分析，为客户提供专业的数据解读和建议，帮助客户更好地了解水质状况。</div>
            <div>(5)产品维护与升级:提供产品的后续维护和升级服务，确保产品的长期稳定运行，并随时跟进技术发展，为客户提供最新的产品体验。</div>
        </div>
        <div class="col-2"></div>
    </div>
</template>

<script>
import '@/assets/iconfont/iconfont.css'

export default{

}
</script>

<style scoped>
div.item{
    margin-bottom: 20px;
    margin-left: 10px;
    font-size: 20px;
}
div.form-check{
    margin-right: 10px;
    border: 1px solid black;
}
</style>