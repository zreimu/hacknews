<#include "itheader.ftl">


<div id="main">
    <div class="container" id="daily">
        <div class="jscroll-inner">
            <div class="daily">

                <#assign  cur_date =''/>
                <#list itvos as vo >
                <#if cur_date != vo.itnews.createdDate?string("yyyy-MM-dd")>
                <#if vo_index gt 0 >
            </div> <#--   上一个要收尾 -->
            </#if>
            <#assign  cur_date =vo.itnews.createdDate?string("yyyy-MM-dd")/>
            <h3 class="date">
                <i class="fa icon-calendar"></i>
                <span>新闻分享 &nbsp; ${vo.itnews.createdDate?string("yyyy-MM-dd")}</span>
            </h3>
            <div class="posts">
                </#if>
                <div class="post">
                    <div class="votebar">
                        <#if  vo.like gt 0 >
                            <button class="click-like up pressed" data-id="${vo.itnews.id!}" title="赞同"><i class="vote-arrow"></i><span class="count">${vo.itnews.likeCount!}</span></button>
                        <#else>
                            <button class="click-like up" data-id="${vo.itnews.id!}" title="赞同"><i class="vote-arrow"></i><span class="count">${vo.itnews.likeCount!}</span></button>
                        </#if>
                        <#if vo.like lt 0>
                            <button class="click-dislike down pressed" data-id="${vo.itnews.id!}" title="反对"><i class="vote-arrow"></i></button>
                        <#else>
                            <button class="click-dislike down" data-id="${vo.itnews.id!}" title="反对"><i class="vote-arrow"></i></button>
                        </#if>
                    </div>
                    <div class="content">
                        <div >
                            <img class="content-img" src="${vo.itnews.image!}" alt="">
                        </div>
                        <div class="content-main">
                            <h3 class="title">
                                <a target="_blank" rel="external nofollow" href="${contextPath}/itnews/${vo.itnews.id!}">${vo.itnews.title!}</a>
                            </h3>
                            <div class="meta">
                                ${vo.itnews.link!}
                                <span>
                                            <i class="fa icon-comment"></i> ${vo.itnews.commentCount!}
                                        </span>
                            </div>
                        </div>
                    </div>
                    <div class="user-info">
                        <div class="user-avatar">
                            <img width="32" class="img-circle" src="${vo.type.image}"></a>
                        </div>


                    </div>

                    <div class="subject-name">${vo.type.name}</a></div>
                </div>


                <#if vo_index == itvos?size >
            </div>  <#--最后有个元素要收尾 -->
            </#if>

            </#list>


        </div>
    </div>
</div>

</div>


<#if pop??>
    <script>
        window.loginpop = ${(pop==0)?c};
    </script>
</#if>

<#include "footer.ftl">