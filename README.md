# EmployeeHome

#### 介绍
sb

## 0、管理者
- `git branch develop`
- `git push -u origin develop`
## 1、开始
- 创建一个workspace文件夹，点击进入
- 打开git，执行`git clone https://gitee.com/causehhc/employee-home.git`
- `cd e*`
## 2、新建xxx功能分支
- 新建分支`git checkout -b feature/xxx origin/develop`
- 先拉取develop中的代码，因为有可能别人已经往上提交过代码了`git pull origin develop`
## 3、开发
- TODO
## 4、add&commit
- `git add .`
- `git commit -m "updated xxx"`
## 4、合并到develop
- 先拉取develop中的代码`git pull origin develop`
- 切到develop分支`git checkout develop`
- 合并<feature/xxx>中的代码到develop中`git merge feature/xxx`
- 提交到develop远程分支上`git push`
- 删除本地的分支`git branch -d feature/xxx`
## 5、发布到master
- 建立发布准备分支`git checkout -b release/tag0.1 origin/develop`
- 切到main分支`git checkout master`
- 将release分支合到main上`git merge release/tag0.1`
- 将合完的代码提交到远程main`git push`
- 切到develop分支`git checkout develop`
- 将release分支上的代码合到develop分支上`git merge release/tag0.1`
- 合完的代码推送到远程的develop分支`git push`
- 删除本地release分支`git branch -d release/tag0.1`