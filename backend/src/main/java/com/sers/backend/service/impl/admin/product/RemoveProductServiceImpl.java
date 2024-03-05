package com.sers.backend.service.impl.admin.product;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.mapper.ProductMapper;
import com.sers.backend.pojo.Product;
import com.sers.backend.pojo.User;
import com.sers.backend.service.admin.product.RemoveProductService;
import com.sers.backend.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class RemoveProductServiceImpl implements RemoveProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public JSONObject remove(Integer id) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        JSONObject resp=new JSONObject();
        if(!user.getAdmin()){
            resp.put("message","无管理员权限");
            return resp;
        }
        Product product=productMapper.selectById(id);
        if(product==null){
            resp.put("message","产品不存在或已被删除");
            return resp;
        }
        productMapper.deleteById(id);
        String path="D:\\temp\\Development project\\SpringBoot\\sers\\web\\src\\assets\\images\\"+id.toString()+".png";
        File toFile=new File(path);
        if (toFile.exists()) {
            toFile.delete();
        }
        resp.put("message","successful");
        return resp;
    }
}
