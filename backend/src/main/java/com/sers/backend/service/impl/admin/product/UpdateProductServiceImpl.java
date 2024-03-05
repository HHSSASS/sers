package com.sers.backend.service.impl.admin.product;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.mapper.ProductMapper;
import com.sers.backend.pojo.Product;
import com.sers.backend.pojo.User;
import com.sers.backend.service.admin.product.UpdateProductService;
import com.sers.backend.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class UpdateProductServiceImpl implements UpdateProductService {
    @Autowired
    private ProductMapper productMapper;

    private void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject update(Integer id, MultipartFile photo, String name, String description, String price) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        JSONObject resp=new JSONObject();
        if(!user.getAdmin()){
            resp.put("message","无管理员权限");
            return resp;
        }
        if(name==null||name.length()==0){
            resp.put("message","名称不能为空");
            return resp;
        }
        if(name.length()>100){
            resp.put("message","名称长度不能大于100");
            return resp;
        }
        if(description==null||description.length()==0){
            resp.put("message","简介不能为空");
            return resp;
        }
        if(description.length()>1000){
            resp.put("message","简介长度不能大于1000");
            return resp;
        }
        Double price_num;
        try{
            price_num=Double.parseDouble(price);
        }catch (NumberFormatException e){
            resp.put("message","请输入正确定价");
            return resp;
        }
        if(price_num<=0||price_num>=10000){
            resp.put("message","请输入正确定价");
            return resp;
        }
        Product product=productMapper.selectById(id);
        if(product==null){
            resp.put("message","产品不存在或已被删除");
            return resp;
        }
        if(photo!=null){
            File toFile=new File(product.getPhoto());
            if (toFile.exists()) {
                toFile.delete();
            }
            try {
                inputStreamToFile(photo.getInputStream(),toFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Product new_product=new Product(product.getId(),product.getPhoto(),name,description,price_num);
        productMapper.updateById(new_product);
        resp.put("message","successful");
        return resp;
    }
}
