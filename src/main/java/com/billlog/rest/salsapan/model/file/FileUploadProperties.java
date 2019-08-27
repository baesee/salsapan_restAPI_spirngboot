package com.billlog.rest.salsapan.model.file;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/*
    @Component가 없어도 Bean으로 사용할 수 있는데 굳이 쓴 이유는,
    IDE에서 Bean Mapping이 안된다는 waring이 발생하는 게 싫어서이다.


    @CongifurationProperties Annotation 에 prefix = "file" 로 선언 된 부분은 application.properties 에 선언한
    file.upload-dir=./uploads
    file 필드에 접근한다는 말이다.

    * 주의사항 *
    추가적으로 설정값을 추가 하고 싶다면 application.properties에 추가하고,
    FileUploadProperties class에 필드를 추가하고 getter setter를 추가하면된다.
    필드명은 camel case 로 연결되기때문에 이름을 작성할때 주의하자.
 */
//@Component
@ConfigurationProperties(prefix="file")
public class FileUploadProperties {
    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }
    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
