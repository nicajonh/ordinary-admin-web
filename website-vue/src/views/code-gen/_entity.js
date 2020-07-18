/**
 * 当前模块所需要的对象、字段验证等信息。
 */
export default {
    // 此方案不行。赋值后其值会一直保存的。
    entity: {
        modelDescription: '',
        urlPrefix: '',
        tableName: '',
        javaPackage: undefined
    },
    entityValidtion: {
        modelDescription: [
            {
                required: true,
                message: '请输入所需模块的简单描述',
                trigger: 'blur'
            },
            {
                min: 2,
                max: 8,
                message: '长度在 2 到 8 个字符',
                trigger: 'blur'
            }
        ],
        urlPrefix: [
            {
                required: true,
                message: '请输入代码生成后的url前缀',
                trigger: 'blur'
            },
            {
                min: 3,
                max: 15,
                message: '长度在 3 到 15 个字符',
                trigger: 'blur'
            }
        ],
        tableName: [
            {
                required: true,
                message: '请输入数据库表名',
                trigger: 'blur'
            },
            {
                min: 2,
                max: 100,
                message: '长度在 3 到 100 个字符',
                trigger: 'blur'
            }
        ],
        javaPackage: [
            {
                required: false
            },
            {
                min: 3,
                max: 200,
                message: '长度在 3 到 200 个字符',
                trigger: 'blur'
            }
        ]
    }
}
