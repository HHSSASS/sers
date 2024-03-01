<template>
    <div class="table-responsive">
        <table class="table table-hover" style="text-align: center;">
            <thead>
                <tr>
                    <th>名称</th>
                    <th>芯片型号</th>
                    <th>购买时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>
                        <span class="record-user-username">Gps</span>
                    </td>
                    <td>
                        <span class="record-user-username">Gps1.0</span>
                    </td>
                    <td>
                        <span>2024.3.1</span>
                        <br>
                        <span>20:30:00</span>
                    </td>
                    <td>
                        <button type="button" class="btn btn-secondary">监测</button>                                                
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
</script>

<style scoped>
div.table-responsive>table>thead>tr>th {
    white-space: nowrap;
}
div.table-responsive>table>tbody>tr>td {
    white-space: nowrap;
}
</style>