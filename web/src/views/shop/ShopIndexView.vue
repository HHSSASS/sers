<template>
    <div class="container" style="width: 1100px;">
        <div style="display: flex;margin-top: 10px;">
            <div style="width: 20px;background-color: rgb(13,110,253);"></div>
            <div>
                <div style="font-size: 50px;font-weight: bold;">“菌”探卫士</div>
                <div style="margin-top: 10px;margin-left: 30px;">基于SERS复合基底的智能水体抗生素监测芯片</div>
            </div>
        </div>
        <div class="title">热销产品</div>
        <div class="row">
            <div v-for="product in products" :key="product.id" class="card border-dark" style="width: 16rem;margin-right: 10px;margin-bottom: 10px;text-align: center;">
                <img :src="require('@/assets/images/'+product.id+'.png')" class="card-img-top" alt="">
                <div class="card-body" style="display: flex;flex-direction: column;justify-content: flex-end;align-items: center;">
                    <h5 class="card-title" >{{ product.name.slice(0,10)+(product.name.length<=10?"":"...") }}</h5>
                    <p class="card-text">￥{{ product.price }}</p>
                    <button class="btn btn-primary" type="button" data-bs-toggle="offcanvas" :data-bs-target="'#product'+product.id" aria-controls="offcanvasExample">产品详情</button>
                    <div class="offcanvas offcanvas-end" tabindex="-1" :id="'product'+product.id" aria-labelledby="offcanvasExampleLabel" style="width: 700px;text-align: left;">
                        <div class="offcanvas-header">
                            <h5 class="offcanvas-title" id="offcanvasExampleLabel">产品详情</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                        </div>
                        <div class="offcanvas-body" style="background-color: rgb(240, 240, 240);">
                            <div style="background-color: white;">
                                <div class="item">产品名称：{{ product.name }}</div>
                                <div class="item">产品简介：{{ product.description }}</div>
                                <div class="item">产品定价：￥{{ product.price }}</div>
                            </div>
                        </div>
                        <div style="box-shadow: 0 0 10px #9b9393;">
                            <div style="font-size: 20px;margin-left: 15px;margin-top: 10px;margin-bottom: 10px;">订单详情</div>
                            <div style="background-color: rgb(240, 240, 240);">
                                <div style="height: 20px;"></div>
                                <div style="background-color: white;margin-left: 20px;margin-right: 20px;">
                                    <div class="item">用户名：{{ $store.state.user.username }}</div>
                                    <div class="item">
                                        <div>收货地址：</div>
                                        <input v-model="product.address" type="text" class="form-control form-control-sm" placeholder="请输入收货地址" style="width: 80%;">
                                    </div>
                                    <div class="item">
                                        <div>购买数量：</div>
                                        <input v-model="product.number" type="number" class="form-control form-control-sm" min=1 max=999 style="width: 10%;">
                                    </div>
                                    <div class="item">
                                        <div >支付方式：</div>
                                        <div class="form-check">
                                            <input v-model="product.wechat" class="form-check-input" type="radio" name="flexRadioDefault" :id="'flexRadioDefault1'+product.id">
                                            <label class="form-check-label" :for="'flexRadioDefault1'+product.id">
                                                <div class="iconfont icon-weixinzhifu" style="font-size: 20px;"></div>
                                            </label>
                                        </div>
                                        <div class="form-check">
                                            <input v-model="product.alipay" class="form-check-input" type="radio" name="flexRadioDefault" :id="'flexRadioDefault2'+product.id"  checked>
                                            <label class="form-check-label" :for="'flexRadioDefault2'+product.id">
                                                <div class="iconfont icon-alipay" style="font-size: 20px;"></div>
                                            </label>
                                        </div>
                                    </div>
                                    <div style="display: flex; justify-content: flex-end;align-items: flex-end;">
                                        <div style="color: red;margin-right: 10px;">{{ product.message }}</div>
                                        <div>支付金额：</div>
                                        <div style="font-size: 25px; margin-right: 10px;color: rgb(13,110,253);">￥{{ product.number>0?product.price*product.number:"?" }}</div> 
                                        <button @click="add_order(product)" type="button" class="btn btn-primary" style="border-radius: 0px;">立即购买</button>
                                    </div>
                                </div>
                                <div style="height: 20px;"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <nav aria-label="Page navigation example">
                <ul class="pagination" style="float: right;">
                    <li @click="click_page(-2)" class="page-item"><a class="page-link" href="#">首页</a></li>
                    <li @click="click_page(page.number)" :class="'page-item '+page.is_active" v-for="page in pages" :key="page.number">
                        <a class="page-link" href="#">{{ page.number }}</a>
                    </li>
                    <li @click="click_page(-1)" class="page-item"><a class="page-link" href="#">尾页</a></li>
                </ul>
            </nav>
        </div>
        <div class="title">产品介绍</div>
        <div class="content">
            <div>“菌”探卫士采用自清洁SERS复合基底，结合纳米技术和光学等原理，能够将目标分子的拉曼信号放大10e6~10e14倍，实现对水体中微量有害物质的高效捕获和灵敏检测。同时，通过微流控SERS芯片集成技术，实现样品的自动输送和处理，简化了检测流程，提高了检测效率和准确性。</div>
            <div style="margin-top: 30px; text-align: center;">
                <img src="@/assets/images/description.png" alt="">     
            </div>
        </div>
        <div class="title">产品优势</div>
        <div class="content">
            <div class="row">
                <div class="col-3" style="padding-left: 30px;">
                    <div class="title2">
                        <div class="iconfont icon-duigou_kuai"></div>
                        <div>高灵敏度</div>
                    </div>
                    <div>利用SERS技术，能够实现对水体中微量抗生素的精准检测，达到单个分子水平的检测能力</div>
                </div>
                <div class="col-3" style="padding-left: 30px;">
                    <div class="title2">
                        <div class="iconfont icon-duigou_kuai"></div>
                        <div>自动化检测</div>
                    </div>
                    <div>采用微流控SERS芯片集成技术，实现样品的自动输送和处理，简化操作流程，提高检测效率</div>
                </div>
                <div class="col-3" style="padding-left: 30px;">
                    <div class="title2">
                        <div class="iconfont icon-duigou_kuai"></div>
                        <div>高稳定性和耐用性</div>
                    </div>
                    <div>复合基底具备出色的稳定性和耐用性，能够在长期使用中保持稳定的检测性能</div>
                </div>
                <div class="col-3" style="padding-left: 30px;">
                    <div class="title2">
                        <div class="iconfont icon-duigou_kuai"></div>
                        <div>多功能应用</div>
                    </div>
                    <div>适用于环境监测、传染病预防与控制、水产养殖、农业灌溉水、医疗废水处理、饮用水安全等多个领域</div>
                </div>
            </div>
        </div>
        <div class="title">产品用法</div>
        <div class="content">
            <div class="usage">
                <div style="font-weight: bold;">样品处理：</div>
                <div>将水样取样后直接加载到芯片中。</div>
            </div>
            <div class="usage">
                <div style="font-weight: bold;">数据传输：</div>
                <div>通过与互联网连接，实现数据实时传输至云端服务器。</div>
            </div>
            <div class="usage">
                <div style="font-weight: bold;">数据分析：</div>
                <div>利用云端数据分析和可视化工具，直观展示监测结果。</div>
            </div>
            <div class="usage">
                <div style="font-weight: bold;">应用场景定制：</div>
                <div>根据客户需求，提供定制化的检测方案和技术支持。</div>
            </div>
        </div>
        <div class="title">配套服务</div>
        <div class="content" style="margin-bottom: 100px;">
            <div class="row">
                <div class="col" style="padding-left: 30px;">
                    <div class="title2">
                        <div class="iconfont icon-duigou_kuai"></div>
                        <div>产品培训与指导</div>
                    </div>
                    <div>为客户提供详细的产品操作培训，确保客户能够熟练、正确地使用产品</div>
                </div>
                <div class="col" style="padding-left: 30px;">
                    <div class="title2">
                        <div class="iconfont icon-duigou_kuai"></div>
                        <div>技术支持与咨询</div>
                    </div>
                    <div>设立专业的技术支持团队，随时解答客户在使用过程中遇到的问题，提供及时、有效的解决方案</div>
                </div>
                <div class="col" style="padding-left: 30px;">
                    <div class="title2">
                        <div class="iconfont icon-duigou_kuai"></div>
                        <div>定制化检测方案</div>
                    </div>
                    <div>根据客户的具体需求，提供定制化的检测方案，包括检测项目、检测频率、数据报告等</div>
                </div>
                <div class="col" style="padding-left: 30px;">
                    <div class="title2">
                        <div class="iconfont icon-duigou_kuai"></div>
                        <div>数据分析与解读</div>
                    </div>
                    <div>对检测结果进行深入的数据分析，为客户提供专业的数据解读和建议，帮助客户更好地了解水质状况</div>
                </div>
                <div class="col" style="padding-left: 30px;">
                    <div class="title2">
                        <div class="iconfont icon-duigou_kuai"></div>
                        <div>产品维护与升级</div>
                    </div>
                    <div>提供产品的后续维护和升级服务，确保产品的长期稳定运行，并随时跟进技术发展，为客户提供最新的产品体验</div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { ref } from "vue";
import $ from 'jquery';
import { useStore } from 'vuex'
import { Offcanvas } from 'bootstrap/dist/js/bootstrap'

export default{
    setup(){
        const store=useStore();
        let products=ref([]);
        let total_products=0;
        let pages=ref([]);
        let current_page=1;
        const add_order=(product)=>{
            product.message="";
            let method=0;
            if(product.wechat) method=1;
            else if(product.alipay) method=2;
            $.ajax({
                url:"http://127.0.0.1:3000/api/shop/add/",
                type:"post",
                headers:{
                    Authorization:"Bearer "+store.state.user.token,
                },
                data:{
                    id:product.id,
                    address:product.address,
                    number:product.number,
                    method:method,
                },
                success(resp){
                    if(resp.message=="successful"){
                        Offcanvas.getInstance('#product'+product.id).hide();
                        product.address="";
                        product.number=null;
                        product.wechat=product.alipay=false;
                    }
                    else{
                        product.message=resp.message;
                    }
                },
            })
        }
        const click_page=page=>{
            let max_pages=parseInt(Math.ceil(total_products/8));
            if(page===-2)page=1;
            else if(page===-1)page=max_pages;
            if(page>=1&&page<=max_pages){
                pull_page(page);
            }
        }
        const update_pages=()=>{
            let max_pages=parseInt(Math.ceil(total_products/8));
            let new_pages=[];
            for(let i=current_page-2;i<=current_page+2;++i){
                if(i>=1&&i<=max_pages){
                    new_pages.push({
                        number:i,
                        is_active:i===current_page?"active":"",
                    });
                }
            }
            pages.value=new_pages;
        }
        const pull_page=page=>{
            current_page=page;
            $.ajax({
                url:"http://127.0.0.1:3000/api/shop/getlist/",
                type:"get",
                headers:{
                    Authorization:"Bearer "+store.state.user.token,
                },
                data:{
                    page:page,
                },
                success(resp){
                    products.value=resp.products;
                    total_products=resp.products_count;
                    update_pages();
                },
            })
        }
        pull_page(current_page);

        return{
            products,
            total_products,
            pages,
            click_page,
            add_order,
        }
    }
}
</script>

<style scoped>
div.title{
    font-size: 30px;
    font-weight: bold;
    margin-top: 80px;
}
div.content{
    margin-top: 40px;
}
div.item{
    margin-bottom: 20px;
    margin-left: 10px;
    font-size: 15px;
    display: flex;
}
div.form-check{
    margin-right: 10px;
}
div.title2{
    display: flex;
    font-weight: bold;
    margin-bottom: 10px;
}
div.usage{
    margin-bottom: 20px;
    display: flex;
}
div.iconfont.icon-duigou_kuai{
    margin-right: 5px;
}
</style>