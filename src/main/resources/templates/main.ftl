<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>
<div>
<@l.logout />
<span><a href="user"> User list</a> </span>
</div>
<div>
    <form method="post" action="main">
        <input type="text" name="text" placeholder="Введите сообщение" autocomplete="off" >
        <input type="text" name="tag" placeholder="Тэг" autocomplete="off">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit"> Добавить </button>
    </form>
</div>
    <div> Список сообщений </div>
    <form method = "get" action = "/main">
        <input type="text" name="filter" autocomplete="off" value=${filter!}>
        <button type="submit"> Найти </button>
    </form>
    <form method="get" action="clear">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit">Сбросить дб</button>
    </form>
</div>
<#list messages as message>
    <div>
        <b> ${message.id} </b>
        <span> ${message.text} </span>
        <i> ${message.tag} </i>
        <strong>${message.authorName}</strong>
    </div>
</#list>
</@c.page>