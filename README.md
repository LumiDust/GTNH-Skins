# GTNH Skins

为 GT New Horizons（Minecraft 1.7.10）提供 Mojang 皮肤分发：服务器按玩家账号获取签名皮肤纹理，并在登录与在线切换时同步给其他玩家。客户端桥接是可选的。

## 支持环境

- GT New Horizons 2.9.0-RC-2
- Minecraft 1.7.10
- Forge 10.13.4.1614
- Java 25
- UniMixins 0.3.1
- SimpleSkinBackport 1.0.3-GTNH（GTNH 自带）

## 功能

- 登录时按玩家当前登录名从 Mojang 获取皮肤
- 使用 Mojang 签名纹理，玩家 UUID、名称和存档身份保持不变
- `/skin set mojang <name>` 设置皮肤来源
- `/skin reset` 或 `/skin clear` 移除手动覆盖并恢复玩家自己的 Mojang 皮肤
- `/skin refresh` 手动刷新来源皮肤
- 支持在线玩家，也支持本世界已确认 UUID 的离线玩家
- 世界内持久化，跨维度共享，服务器重启后自动恢复
- classic、slim、64×32 与 64×64 皮肤
- 观察者在线刷新，保留装备、效果、骑乘与其他玩家状态
- Mojang 服务、磁盘、网络同步或客户端桥接失败时继续正常游戏，保留当前有效皮肤并给出提示
- 请求合并、超时、限流处理与每玩家操作冷却

皮肤格式解析与 slim 模型由 GTNH 的 SimpleSkinBackport 负责，本模组不重复实现渲染器。

## 安装

1. 确认服务器使用 GTNH 2.9.0-RC-2 的 Java 25 发行包。
2. 将 `gtnhskins-1.0.0.jar` 放入服务器 `mods` 目录。
3. 服务器已自带 UniMixins 与 SimpleSkinBackport，无需额外安装。
4. 需要本人第一人称与 F5 视角实时生效的玩家，把同一个 JAR 放进客户端 `mods` 目录。
5. 启动服务器，玩家正常登录即可。

客户端桥接是可选的。没有桥接的客户端仍可加入服务器，并能看到其他玩家由服务器分发的皮肤；本人的第一人称显示可能保持原有皮肤。

## 命令

```text
/skin set mojang <Mojang用户名>
/skin set mojang <Mojang用户名> <在线玩家或已知UUID>
/skin reset [在线玩家或已知UUID]
/skin clear [在线玩家或已知UUID]
/skin refresh [在线玩家或已知UUID]
/skin config reload
```

普通玩家只能操作自己，默认冷却 30 秒，可在配置中调整。OP 或控制台可以操作在线玩家和本世界已确认 UUID 的离线玩家。

离线服务器中的玩家身份仍然使用离线 UUID。自动恢复通过玩家登录名查询对应 Mojang 账号；如果登录名不是 Mojang 账号，可由管理员用 `set mojang` 指定来源。

## 配置

首次启动生成 `config/gtnhskins.cfg`：

| 配置项 | 默认值 | 说明 |
| --- | --- | --- |
| 玩家命令冷却 | 30 秒 | 同一玩家的两次皮肤命令最小间隔 |
| Mojang 请求超时 | 10 秒 | 单次查询的等待上限 |
| 请求线程 | 4 | 并发查询线程数 |
| 请求队列 | 64 | 等待处理的查询上限 |
| 成功缓存 | 300 秒 | 查询结果的缓存时长 |

Mojang 不可用时玩家仍可登录和继续游戏。设置或刷新失败会保留当前皮肤，并在聊天和服务器日志中说明原因。

## 数据位置

- 配置：`config/gtnhskins.cfg`
- 皮肤状态：`<世界目录>/gtnhskins/<uuid>.json`

数据随世界保存，跨维度共享，不同世界互不影响。

## 当前范围

首版来源为 Mojang。以下功能不在当前版本内：

- URL、Ely.by、MineSkin、本地图片与自定义提供器
- SkinShuffle 与图形化换肤界面
- 单人、局域网与混合插件服务端
- SkinPort、CustomSkinLoader 等额外皮肤加载链
- 跨世界全局皮肤存储

正版 `online-mode=true` 的完整登录矩阵需要用真实正版账号单独验证；离线服务器、Mojang 签名纹理、双客户端观察、classic/slim 与重启恢复已在 GTNH 2.9.0-RC-2 上验证。

## 构建

需要 Java 25、Java 8/17 辅助工具链，以及可访问 GTNH Maven 的网络：

```powershell
$env:JAVA_HOME = "path\to\jdk25"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"

.\gradlew.bat clean build --no-daemon
```

产物位于 `build/libs/gtnhskins-1.0.0.jar`，`-dev` 后缀的 JAR 仅用于开发环境。

## 致谢

感谢 [Lionarius](https://github.com/Suiranoil) 创作并开源 [SkinRestorer](https://github.com/Suiranoil/SkinRestorer)。本项目在 GTNH 1.7.10 上的皮肤分发方案、配置结构与命令约定，来自对这份工作的借鉴与应用。

感谢 [GT New Horizons](https://github.com/GTNewHorizons) 团队维护整合包与构建工具链，并提供 [SimpleSkinBackport](https://github.com/GTNewHorizons/SimpleSkinBackport) 承担 1.7.10 的皮肤纹理解析与 slim 模型渲染。

感谢 [UniMixins](https://github.com/LegacyModdingMC/UniMixins) 提供 1.7.10 上可用的 Mixin 运行环境。

## 许可

MIT，详见 [LICENSE](LICENSE)。
