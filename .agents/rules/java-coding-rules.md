---
trigger: always_on
---

# Java 程式碼編寫規則

此規則應用在 `.java` 的 Java 程式碼使用。

## 編寫規則

* 所有 `public` 的方法都要有對應的單元測試。
* 所有的方法（method）都要有 JavaDoc。
* 如果修改以存在的 `public` 方法（method）必須通過單元測試。

## 命名規範

命名必須語意清晰，禁止隨意縮寫。

* 變數使用 camelCase。
* 常數使用 UPPER_SNAKE_CASE。
* 類使用 PascalCase。
* 如未列出者，使用一般 Java 開發命名規則命名。

## 絕對禁止

* 禁止在未經同意的情況下增加、修改、刪除依賴，如：`build.gradle`、`pom.xml`。
* 調試期間不要改寫已存在的方法或新增不相關的方法。