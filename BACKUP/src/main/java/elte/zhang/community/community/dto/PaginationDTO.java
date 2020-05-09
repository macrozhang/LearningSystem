package elte.zhang.community.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showProvious;
    private boolean showFirstPage;
    private boolean showNext;

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }

    public boolean isShowProvious() {
        return showProvious;
    }

    public void setShowProvious(boolean showProvious) {
        this.showProvious = showProvious;
    }

    public boolean isShowFirstPage() {
        return showFirstPage;
    }

    public void setShowFirstPage(boolean showFirstPage) {
        this.showFirstPage = showFirstPage;
    }

    public boolean isShowNext() {
        return showNext;
    }

    public void setShowNext(boolean showNext) {
        this.showNext = showNext;
    }

    public boolean isShowEndPage() {
        return showEndPage;
    }

    public void setShowEndPage(boolean showEndPage) {
        this.showEndPage = showEndPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    private boolean showEndPage;
    private Integer page;
    private List<Integer>pages = new ArrayList<>();
    private Integer totalPage;
    public void setPagination(Integer totalCount, Integer page, Integer size) {


        if(totalCount%size == 0){
            totalPage = totalCount / size;
        }else{
            totalPage = totalCount / size + 1;
        }
        if(page<1){
            page = 1;
        }else if(page>totalPage){
            page = totalPage;
        }
        this.page = page;
        pages.add(page);
        for(int i = 1 ;i <=3 ;i++){
            if(page - i > 0 ){
                pages.add(0,page-i);
            }
            if(page + i <= totalPage){
                pages.add(page+i);
            }
        }

        //如果当前页数是第一页和最后一页就不展示前一页和后一页
        if(page == 1){
            showProvious = false;
        }else {
            showProvious = true;
        }
        if(page == totalPage){
            showNext = false;
        }else {
            showNext = true;
        }

        //如果当前这个分页列表包含了第一页，就不用显示显示回到第一页，最后一页同理
        if(pages.contains(1)){
            showFirstPage = false;
        }else{
            showFirstPage = true;
        }
        if(pages.contains(totalPage)){
            showEndPage = false;
        }else{
            showEndPage = true;
        }
    }
}
