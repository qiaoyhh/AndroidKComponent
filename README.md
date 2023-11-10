# AndroidKComponent
Android组件化新思路+自动化创建组件模版

在基础组件化方案中：A B两个组件之间有业务交互，我们一般需要获取对方组件接口类(忽略底层实现)，在业务规模较小情况下是没有什么大问题的；当A组件需要 B C D...等等通信后，我们A组件就变得比较乱，后面我们根本理不清楚组件之间的调用关系，为了解决这个问题，我引入Builder得概念，把需要的组件业务收敛，更容易维护。
