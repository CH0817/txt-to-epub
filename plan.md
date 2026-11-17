# txt-to-epub 轉換為 Flutter 計畫

> 本文件供 AI 在**新的 Flutter 專案**中依序執行開發，所以內容自成一體，包含原 Java 專案的行為規格（附錄 A）。
> Java 專案**保留不動，僅作為行為基準與資源來源**。專案位置不寫死在本文件，改由環境變數指定：
>
> | 環境變數 | 意義 |
> |---|---|
> | `TXT_TO_EPUB_JAVA_DIR` | Java 專案根目錄（本文件所在目錄；取得方式見 2.2） |
> | `TXT_TO_EPUB_FLUTTER_DIR` | Flutter 專案根目錄（階段 0 建立） |
>
> 本文件以 `$TXT_TO_EPUB_JAVA_DIR`、`$TXT_TO_EPUB_FLUTTER_DIR` 表示這兩個變數的值（Windows PowerShell 的寫法為 `$env:TXT_TO_EPUB_JAVA_DIR`，見 2.2）。任一變數未設定時，AI 不得自行猜測路徑，先請使用者設定。

## 1. 已確認的決策

| 項目 | 決定 |
|---|---|
| 目標平台 | Windows、macOS（桌面）。不含 iOS、Android、Linux、Web。手機版暫不做，待桌面版穩定後再評估 |
| 開發機與驗證分工 | 開發機有兩台：MacBook Pro M1（macOS，Apple Silicon）與 Windows（x64）。AI 在哪一台執行，就驗證該機能驗證的平台：Mac 驗證 macOS，Windows 驗證 Windows。另一台尚未驗證的平台，列入待人工驗證清單，待使用者在該機再次啟動 AI 執行。Flutter 專案為獨立的 git 儲存庫（見規則 11、M9） |
| App 識別 | 組織識別 `tw.com.rex`（`flutter create --org tw.com.rex`），bundle id 由此產生，不使用 `com.example.*` |
| 最低系統版本 | macOS 與 Windows 沿用 Flutter 3.50 範本預設值，並記錄於 README。若 Mac 使用 Xcode 27，macOS 部署目標不得低於 12.0（低於此值建置會失敗） |
| 範圍 | 功能與 Java 版對等，不新增功能 |
| 開發依據版本 | Flutter 3.50.0（stable，framework revision `ab2912035e`，2026-11-09）、Dart 3.14.0（stable，2026-11-04），macOS arm64（Apple Silicon）與 Windows x64。**Mac 與 Windows 兩台必須使用完全相同的版本與 channel**。AI 開始前先確認版本（指令見 2.2）；與上述版本不同時，不得繼續，先切換到相同版本（切換指令見 2.2）或通知使用者。`pubspec.yaml` 的 `environment` 使用 `flutter create` 產生的值，不手動調低。升級 Flutter 或 Dart 後，要重跑 `flutter analyze` 與 `flutter test`，並重新核對第 6 節的版本注意事項 |

## 2. 給執行 AI 的規則

1. 不得修改 Java 專案的任何檔案。只能讀取，或把 `$TXT_TO_EPUB_JAVA_DIR/src/main/resources/style/` 複製到 Flutter 專案。
2. 第 6 節列出的套件可直接加入。清單外的依賴（新增、修改、刪除）先向使用者確認。
3. 所有 public 函式要有單元測試。所有函式與類別要有 dartdoc 註解（`///`）。
4. 命名：變數與函式 `lowerCamelCase`，類別 `UpperCamelCase`，常數 `lowerCamelCase`（Dart 慣例），檔名 `snake_case`。禁止隨意縮寫。
5. 每個階段結束時執行 `flutter analyze` 與 `flutter test`，皆通過才進入下一階段。
6. 附錄 B 的原程式問題已由使用者決定處理方式，**以附錄 B 為準**，與附錄 A（Java 原行為）衝突時採附錄 B。
7. **執行分工**：除 2.1 的人工步驟外，其餘一律由 AI 直接執行，不把可自動化的工作交還給使用者。
8. AI 無法執行的驗證，列入 README 的「待人工驗證」清單並告知使用者，不得回報為已通過。
9. 清理暫存或重複檔案時不得刪除，列出路徑請使用者手動刪除（M5）。
10. **自動選用對應指令**：AI 開始前先偵測目前作業系統（指令見 2.2），依 2.2 對照表選用 Mac 或 Windows 的指令，不得假設目前是哪一台。目前作業系統無法執行的驗證（例如 Mac 上建置 Windows，Windows 上建置 macOS）依規則 8 處理。
11. **git**：AI 可在每個階段通過 `flutter analyze` 與 `flutter test` 後，於 Flutter 專案儲存庫 commit，訊息簡述該階段內容。不得 push、force push、`reset --hard` 或改寫歷史。不得在 Java 專案儲存庫 commit（規則 1）。

### 2.1 必須由人工處理的步驟

| # | 人工步驟 | 原因 | 預估工時 |
|---|---|---|---|
| M1 | 以實際小說 txt 在 Java 版與 Flutter 版各轉一次，目視比較橫排、直排、目錄、封面、章節數 | 需要使用者的資料與判斷 | 1–2 小時 |
| M2 | 在 Calibre、Apple Books 等閱讀器目視確認顯示 | AI 無法操作圖形閱讀器 | 0.5–1 小時 |
| M3 | 決定是否新增、修改、刪除第 6 節以外的依賴 | 規則 2 | 每次 10–15 分鐘 |
| M4 | 決定附錄 A 沒寫明的細節與規格衝突 | 階段 0 第 3 點 | 每次 10–15 分鐘 |
| M5 | 手動刪除 AI 列出的暫存或重複檔案 | 全域檔案保護規則 | 5–10 分鐘 |
| M6 | 在 Windows 機器安裝 Visual Studio（「使用 C++ 的桌面開發」工作負載）與 Flutter（版本須與 Mac 相同） | 需要系統管理員權限與安裝程式互動 | 1–2 小時（含下載） |
| M7 | 若 `flutter doctor` 出現網路錯誤（例如連 `https://storage.googleapis.com/` 憑證握手失敗，疑似公司代理或防火牆），協助排除 | 需要網路管理權限或公司憑證 | 無此問題則免；有則 15–60 分鐘 |
| M8 | 在 Mac 安裝 Xcode（版本以 `flutter doctor` 的要求為準）、接受授權，並安裝 CocoaPods。macOS 版本需符合所選 Xcode 的要求，必要時先升級 | 需要 Apple ID 登入與授權，下載量大 | 1–2 小時（含下載，不含升級 macOS） |
| M9 | 在 GitHub 建立 Flutter 專案的儲存庫，並把網址提供給 AI 設定 remote（階段 0 建立專案後、兩台機器同步前完成）；每次換機器前自行 `git push` | 需要使用者的 GitHub 帳號 | 10–15 分鐘 |

各步驟在 Mac 與 Windows 的指令見 2.2。人工步驟合計約 4–8 小時，不含 M3、M4 的決定次數。

### 2.2 指令對照（Mac / Windows）

各步驟指令依目前機器選用對應欄（規則 10）。人工步驟請依自己所在的機器使用對應欄。Mac 指令用 zsh，Windows 指令用 PowerShell。`<...>` 為佔位符，需替換成實際值。

| 用途 | Mac（macOS） | Windows（PowerShell） |
|---|---|---|
| 偵測作業系統 | `uname -s`（輸出 `Darwin`） | `$env:OS`（輸出 `Windows_NT`） |
| 設定環境變數（永久） | `echo 'export TXT_TO_EPUB_FLUTTER_DIR="<路徑>"' >> ~/.zshrc && source ~/.zshrc`（`TXT_TO_EPUB_JAVA_DIR` 同理） | `setx TXT_TO_EPUB_FLUTTER_DIR "<路徑>"`（需重開終端機才生效；`TXT_TO_EPUB_JAVA_DIR` 同理） |
| 讀取環境變數 | `$TXT_TO_EPUB_FLUTTER_DIR` | `$env:TXT_TO_EPUB_FLUTTER_DIR` |
| 確認 Flutter / Dart 版本 | `flutter --version`、`dart --version` | 同左 |
| 切換到指定 Flutter 版本（SDK 以 git 安裝時） | `git -C "$(dirname "$(dirname "$(which flutter)")")" checkout 3.50.0`，再 `flutter --version` | `git -C (Split-Path (Split-Path (Get-Command flutter).Source)) checkout 3.50.0`，再 `flutter --version` |
| 檢查整體環境 | `flutter doctor -v` | 同左 |
| 安裝 Mac 開發工具（M8） | `xcode-select --install`、`sudo xcodebuild -license accept`、`brew install cocoapods`（Xcode 本體由 App Store 安裝） | 不適用 |
| 安裝 Visual Studio（M6） | 不適用 | 從 Visual Studio 官網下載安裝，勾選「使用 C++ 的桌面開發」工作負載。實際版本以 Windows 上 `flutter doctor` 的要求為準 |
| 列出裝置 | `flutter devices` | 同左 |
| 同步專案 | 切換機器前：`git add -A`、`git commit -m "<訊息>"`（AI 可執行），`git push`（由使用者執行）；另一台 `git pull` | 同左 |
| 取得 Java 專案 | `git clone https://github.com/CH0817/txt-to-epub.git "$TXT_TO_EPUB_JAVA_DIR"` | `git clone https://github.com/CH0817/txt-to-epub.git "$env:TXT_TO_EPUB_JAVA_DIR"` |
| 初始化 Flutter 專案的 git（建立專案後） | `git -C "$TXT_TO_EPUB_FLUTTER_DIR" init`；M9 完成後 `git -C "$TXT_TO_EPUB_FLUTTER_DIR" remote add origin <GitHub 網址>` | `git -C "$env:TXT_TO_EPUB_FLUTTER_DIR" init`；M9 完成後 `git -C "$env:TXT_TO_EPUB_FLUTTER_DIR" remote add origin <GitHub 網址>` |
| 建立專案 | `flutter create --org tw.com.rex --project-name=txt_to_epub_flutter --platforms=windows,macos "$TXT_TO_EPUB_FLUTTER_DIR"` | `flutter create --org tw.com.rex --project-name=txt_to_epub_flutter --platforms=windows,macos "$env:TXT_TO_EPUB_FLUTTER_DIR"` |
| 執行 App（M1） | `flutter run -d macos` | `flutter run -d windows` |
| 整合測試 | `flutter test integration_test -d macos` | `flutter test integration_test -d windows` |
| 建置驗證 | `flutter build macos` | `flutter build windows` |
| 執行 EPUBCheck | `java -jar tool/epubcheck.jar <epub 路徑>` | 同左 |
| 安裝閱讀器（M2） | `brew install --cask calibre` | `winget install calibre.calibre` |
| 刪除 AI 列出的檔案（M5） | `rm -- "<路徑>"` | `Remove-Item "<路徑>"` |

M3、M4 為決定事項，無指令。

## 3. 目標架構

```
txt_to_epub_flutter/
├─ pubspec.yaml
├─ assets/
│  ├─ style/                     # 從 Java 專案 src/main/resources/style 原樣複製
│  │  ├─ style-reset.css  style-standard.css  style-advance.css  style-check.css
│  │  ├─ horizontal/book-style.css
│  │  └─ vertical/book-style.css
│  └─ opencc/                    # OpenCC 字典（見 5.3），STCharacters.txt、STPhrases.txt
├─ lib/
│  ├─ main.dart
│  ├─ domain/                    # 純資料，無 Flutter 依賴
│  │  ├─ chapter_type.dart       # enum ChapterType { regex, wordCount }
│  │  ├─ typesetting.dart        # enum Typesetting { horizontal, vertical }
│  │  ├─ display_style.dart      # DisplayStyle 與 horizontal / vertical 兩個常數實例
│  │  ├─ convert_info.dart
│  │  ├─ txt_content.dart
│  │  └─ book.dart
│  ├─ core/                      # 純 Dart，可在 Isolate 執行
│  │  ├─ txt_decoder.dart
│  │  ├─ special_symbol_replacer.dart
│  │  ├─ list_util.dart
│  │  └─ chinese/
│  │     ├─ simplified_to_traditional.dart   # 最長匹配轉換器
│  │     └─ conversion_dictionary.dart       # 字典載入與 trie
│  ├─ chapter/
│  │  ├─ chapter_finder.dart
│  │  ├─ regex_chapter_finder.dart
│  │  └─ word_count_chapter_finder.dart
│  ├─ epub/
│  │  ├─ book_creator.dart
│  │  ├─ content_xhtml_builder.dart
│  │  ├─ cover_xhtml_builder.dart
│  │  ├─ table_of_contents_builder.dart
│  │  ├─ navigation_document_builder.dart
│  │  ├─ opf_builder.dart
│  │  ├─ container_xml.dart
│  │  └─ epub_packager.dart              # 產生 zip bytes
│  ├─ service/
│  │  └─ epub_service.dart               # 對應 Java EpubService.process
│  ├─ platform/
│  │  ├─ file_access.dart                # 抽象：選檔、讀取、儲存、開啟結果（測試時以 provider 覆寫）
│  │  └─ file_access_desktop.dart
│  └─ ui/
│     ├─ convert_page.dart
│     ├─ convert_controller.dart         # 取代 Java Presenter
│     ├─ convert_state.dart
│     └─ widgets/
└─ test/                                 # 結構與 lib 對應
   └─ fixtures/                          # 測試用小型 txt 與預期輸出（依附錄 A 手寫）
```

### Java 與 Flutter 對應

| Java | Flutter / Dart |
|---|---|
| `EpubConvertFrame`（Swing） | `ConvertPage`（`Scaffold` + `Form`），桌面限制最大寬度置中 |
| `EpubConvertPresenter` + `EpubConvertView` | `ConvertController`（`Notifier`）+ 不可變 `ConvertState`，畫面只觀察狀態 |
| `JFileChooser` | `file_picker`，經 `FileAccess` 抽象 |
| `JOptionPane` | `AlertDialog` / `SnackBar` |
| `Desktop.open(outputPath)` | `url_launcher` 開資料夾 |
| Lombok `@Builder` | 不可變 class + `copyWith` |
| `StringBuilder` 組 XML | 純函式回傳 `String`，文字節點經跳脫 |
| JAXB `Container` | 固定字串常數 |
| 暫存目錄 + `EpubChecker --save` | 記憶體內 `Archive`，不寫暫存檔 |
| 執行緒 | `Isolate.run` |
| slf4j | `logging` |

### 3.1 開發前環境準備

階段 0 開始前，AI 在目前這台機器上確認下列項目（Mac 與 Windows 各自執行一次，項目名稱前的括號標明適用機器，未標示的兩台都要）。

| 項目 | 需求 | 處理 |
|---|---|---|
| Flutter / Dart | 與第 1 節「開發依據版本」相同；能解析第 6 節所有套件（Dart 3.12 以上，由 `flutter_riverpod` 要求；Flutter 3.41 以上，由 `url_launcher` 要求） | AI 確認版本 |
| （Mac）Xcode、Command Line Tools、CocoaPods | 建置 macOS（版本以 `flutter doctor` 的要求為準） | M8 |
| （Windows）Visual Studio 的「使用 C++ 的桌面開發」工作負載 | 建置與執行 Windows 版 | M6 |
| JDK | 執行 `epubcheck.jar`（階段 2） | AI 檢查 `java -version`，沒有就記錄為「未執行」，列入待人工驗證清單 |
| 網路 | `flutter pub get`、下載 Flutter 資產、epubcheck 皆需連外 | 有問題時 M7 |
| 專案位置環境變數 | `TXT_TO_EPUB_JAVA_DIR`、`TXT_TO_EPUB_FLUTTER_DIR`（見本文件開頭） | AI 檢查兩者皆已設定，且 Java 目錄存在（不存在則依 2.2 clone）；Flutter 目錄的上層目錄不存在時由 AI 建立 |

完成該機對應的 M8（Mac）或 M6（Windows），及 M7（若需要）後，AI 重新執行 `flutter doctor -v`，確認該機適用的項目皆為通過（Mac：Flutter、Xcode；Windows：Flutter、Visual Studio），Chrome 可忽略，本專案不含 Web，並把結果記錄於 README。該機任一適用項目仍失敗，不進入階段 0。

## 4. 階段計畫

### 階段 0：建立專案

1. 依 2.2 的「建立專案」指令建立 Flutter 專案，設定 `flutter_lints`，並依 2.2 初始化 git 儲存庫、做第一次 commit。
2. 複製 CSS 到 `assets/style/`，在 `pubspec.yaml` 宣告 assets。
3. **不使用 golden 基準檔**。使用者暫不提供 Java 版轉出的基準 EPUB，由使用者人工測試驗收。AI 改以附錄 A 的規格撰寫測試：
   - 在 `test/fixtures/` 建立小型 txt（約 5–10 章，另有一份超過 100 章的程式產生資料，用來測分冊）。
   - 預期輸出（章節標題、檔名、OPF 項目順序、各 xhtml 的關鍵片段）依附錄 A 手寫，不要自行推測規格以外的行為。
   - 附錄 A 沒寫明的細節，先問使用者，不要自行決定。
   - 之後若使用者提供基準檔，再補做逐檔比對。

### 階段 1：純 Dart 核心

依序完成，每項附單元測試：

1. `domain/*`：enum 與 model。`TxtContent(title, contentList, xhtmlName)`。
2. `core/special_symbol_replacer.dart`：`& < > " '` → `&amp; &lt; &gt; &quot; &apos;`，**`&` 最先處理**。
3. `core/list_util.dart`：`separateDataList(list, size)`。邊界：空清單 → 空；剛好 100 → 1 組；101 → 2 組。
4. `core/txt_decoder.dart`：見 5.1。
5. `chapter/*`：見附錄 A.3、A.4。
6. `epub/book_creator.dart`：每 100 章一本，書名見附錄 A.5。
7. `core/chinese/*`：見 5.3。

**完成標準**：單元測試通過；以 fixtures 跑章節切分，章節數、標題、內文行數與附錄 A.4 的規則一致。

### 階段 2：EPUB 產生

1. 依附錄 A.6–A.11 逐一移植各 builder，輸出字串與附錄 A 描述的結構相同（換行字元例外，統一用 `\n`）。
2. `epub_packager.dart`：見 5.2。
3. `epub_service.dart`：輸入 `ConvertInfo` 與 txt bytes，輸出 `List<EpubFile>`（檔名 + bytes）。**此層不碰檔案系統**，方便測試與跨平台。
4. 測試比對時忽略 `dc:identifier` 的 UUID 與 `dcterms:modified`。

**完成標準**：單元測試通過，且 AI 完成下列檢查：
- 以程式解開 zip（`ZipDecoder().decodeBytes(bytes, verify: true)`），驗證 `mimetype` 順序與 stored、所有 XML 可解析、OPF 引用的檔案都存在。
- 本機有 `java` 時，取得 `epubcheck.jar`（官方 GitHub release，外部開發工具，不寫入 `pubspec.yaml`，存放於 `tool/`），對橫排、直排、有封面、無封面各一份執行，目標 0 errors，結果記錄於 README。無 `java` 或下載失敗時記錄為「未執行」，列入待人工驗證清單，不阻擋下一階段。

### 階段 3：UI 與平台層

0. **PoC（AI）**：先寫最小範例驗證 `file_picker` 的選檔與 `saveFile`。Mac 上在 macOS、Windows 上在 Windows 實際執行。
1. `ConvertPage` 欄位與預設值見附錄 A.1。
2. `ConvertController`：
   - 驗證訊息沿用「請選擇txt檔案」「請選擇輸出路徑」，兩者皆缺時以換行合併。
   - 以 `Isolate.run` 呼叫 `EpubService`。字典與 CSS 需在主 isolate 用 `rootBundle` 先讀成字串，再傳入 isolate（isolate 內無法使用 `rootBundle`）。
   - 狀態：`idle` / `converting` / `success(outputs)` / `failure(message)`。
3. `FileAccess` 抽象與桌面實作，見 5.4。

### 階段 4：測試與收尾

- Widget 測試：表單切換預設值、驗證訊息、轉換中按鈕狀態。
- 整合測試（AI）：小型 txt 跑完整流程，解開產出的 zip 驗證結構與關鍵內容。依 2.2 的「整合測試」指令，在目前機器的平台執行（Mac：macOS；Windows：Windows）。
- 建置驗證（AI）：依 2.2 的「建置驗證」指令，建置目前機器的平台。
- README（AI）：建置、使用、已知限制、與 opencc4j 的差異、Java 與 Dart 正則差異、EPUBCheck 結果、待人工驗證清單。

### 階段 5：開發完成後測試（AI）

階段 0–4 全部完成後，AI 以乾淨狀態（`flutter pub get`）執行下列測試。指令依規則 10 選用 2.2 對應欄。

| # | 測試項目 | 通過標準 |
|---|---|---|
| T1 | 靜態分析：`flutter analyze` | 無警告、無錯誤 |
| T2 | 單元與 widget 測試：`flutter test` | 全數通過 |
| T3 | 規格與邊界測試：附錄 A 的 fixtures、第 7 節邊界案例、5.1 編碼偵測案例、5.3 的 `s2t_cases.txt` | 全數通過（已含在 T2，失敗時需單獨列出） |
| T4 | EPUB 結構驗證：橫排、直排 × 有封面、無封面共 4 份，解開 zip 檢查 `mimetype` 順序與 stored、XML 可解析、OPF 引用檔案都存在 | 4 份皆通過 |
| T5 | EPUBCheck（階段 2 的做法） | 4 份皆 0 errors；無 `java` 或下載失敗時記錄為「未執行」，不算失敗 |
| T6 | 端對端轉換：超過 100 章的 fixture 轉換後得到 2 本，檔名、書名、章節檔名序號符合附錄 A.4、A.5 | 符合 |
| T7 | 整合測試（2.2 的「整合測試」指令） | 全數通過 |
| T8 | 建置驗證（2.2 的「建置驗證」指令，目前機器的平台） | 建置成功 |

流程：

1. 依序執行 T1–T8。每個項目**最多執行三次**（首次加上最多兩次修正後重跑），三次計數各項目獨立。
2. 項目失敗時，AI 分析原因並修正程式，只重跑該項目。通過後，因修正可能影響先前已通過的項目，回頭重跑該項目之前的所有項目作為迴歸檢查；迴歸檢查中失敗的項目，其執行次數加一。
3. 任一項目第三次執行仍失敗，AI 立即**終止執行**，不再修正、不 commit 為完成，並向使用者回報：該項目與其他失敗項目的錯誤摘要、各次已嘗試的修正、推測的原因、建議的下一步。同時寫入 README 的待處理清單。
4. 修正時不得為了通過而刪除、跳過或弱化測試（放寬斷言、改預期值）。預期值以附錄 A、B 為準；懷疑測試或規格本身有誤時，停止修改並詢問使用者（M4）。
5. 修正若需要清單外的依賴，仍須先詢問使用者（規則 2）。
6. 因目前機器無法執行（規則 8、10）或環境問題（網路中斷）而無法執行的項目，記錄為「未執行」並列入待人工驗證清單，不算失敗，也不計入次數。
7. 每個項目的執行次數與結果記錄於 README 的「測試結果」。T1–T8 全部通過（或僅有「未執行」項目）後，進入第 8 節完成定義，並依規則 11 commit。

## 5. 技術設計與風險

### 5.1 txt 編碼解碼

Dart 只內建 UTF-8 / Latin-1 / ASCII。採用純 Dart 套件，所有平台一致：

- GBK 與 Big5：統一使用 `enough_convert`（純 Dart，支援 Dart 3；GBK 相容 GB2312）。其 codec 有 `allowInvalid` 參數。

解碼流程（已決定加入編碼偵測，取代 Java 版「依序嘗試、第一個成功即採用」的做法）：

1. **BOM 優先**：開頭為 `EF BB BF` 視為 UTF-8，`FF FE` / `FE FF` 視為 UTF-16 LE / BE，解碼後**移除 BOM**（U+FEFF）。
2. **嚴格 UTF-8**：`utf8.decode(bytes, allowMalformed: false)`，成功即採用。Dart 預設會把非法位元組換成 U+FFFD 而不丟例外，所以一定要用嚴格模式。
3. **Big5 與 GBK 擇優**：兩者都以「遇到無效序列即失敗」的方式解碼（使用 `allowInvalid: false`；若實測仍會靜默取代，就檢查結果是否含 U+FFFD，有則視為失敗）。
   - 只有一個成功：採用該編碼。
   - 兩個都成功：計分，分數高者勝。
     1. **可疑字元比例**：統計不在下列範圍的字元佔比：ASCII、空白與換行、CJK 統一表意文字 U+4E00–U+9FFF、CJK 標點 U+3000–U+303F、全形符號 U+FF00–U+FFEF、一般標點 U+2000–U+206F。比例較低者勝。若差距小於 0.5 個百分點，視為平手。
     2. **平手時比較簡繁特徵字**：用簡轉繁字典（5.3）取得「只出現在簡體的字」與「只出現在繁體的字」，統計各解碼結果中的數量。繁體特徵字較多者採 Big5，簡體特徵字較多者採 GBK。
     3. 仍平手則採 Big5。
   - 兩個都失敗：嘗試 UTF-16（無 BOM 時先試 LE，再試 BE，並檢查結果不含 U+FFFD 與大量控制字元）。
   - 全部失敗：丟出「無法判斷 txt 編碼」，UI 顯示錯誤訊息。
4. 測試資料不必依賴外部檔案：測試中用 `enough_convert` 的 Big5、GBK encoder 把一段繁體與一段簡體文字編成 bytes，再驗證偵測結果。需涵蓋：UTF-8（有 / 無 BOM）、Big5 繁體、GBK 簡體、UTF-16 LE（有 BOM）、亂碼二進位。
5. 偵測只取檔案前 64 KB 做評分以節省時間，確認編碼後再完整解碼。若完整解碼失敗，則回退到評分第二名。

### 5.2 EPUB 打包（取代 EPUBCheck 的 `--save`）

使用 `archive` 套件。規則（EPUB 規格要求）：

- `mimetype` 必須是 zip 的**第一個項目**，**不壓縮（stored）**，內容 `application/epub+zip`，無 extra field。
- 其餘項目用 deflate。
- 路徑結構：
  - `META-INF/container.xml`
  - `item/standard.opf`
  - `item/navigation-documents.xhtml`
  - `item/xhtml/p-cover.xhtml`（有封面時）、`item/xhtml/p-toc.xhtml`、`item/xhtml/p-0001.xhtml` ...
  - `item/style/*.css`
  - `item/image/<封面檔名>`（有封面時）

### 5.3 簡轉繁（純 Dart）

現況：opencc4j 的 `ZhConverterUtil.toTraditional(source, Segments.defaults())`，之後套用後處理。

自行以純 Dart 實作（不使用 OpenCC 的原生或 FFI 套件）：

1. 字典：從 OpenCC 專案取 `STCharacters.txt`（單字）與 `STPhrases.txt`（詞組），放 `assets/opencc/`。OpenCC 為 Apache 2.0，需在專案附授權與來源說明。對應 OpenCC 設定 `s2t`（簡體 → 繁體，不含地區用詞）。
2. 演算法：以 trie 做**最長匹配**。從左到右，在每個位置先找 `STPhrases` 的最長詞，找不到再查 `STCharacters`，都沒有則原字輸出。
3. 效能：字典載入一次後快取。詞組表約數萬筆，用 `Map<int, Node>` 的 trie。
4. 後處理（必須保留，與 `SimplifiedToTraditionalConverter` 一致）：
   - 字：`羣→群`、`孃→娘`、`裏→裡`、`纔→才`
   - 名詞：`樑習→梁習`
5. **差異預期**：opencc4j 使用自己的字典，與 OpenCC 的 `s2t` 在少數詞上結果不同。處理方式：
   - 與 opencc4j 的字典差異只需記錄在 README，不需逐詞比對（使用者已確認）。
   - 準備一份 `test/fixtures/s2t_cases.txt`（約 30 句簡體 → 預期繁體，預期值手寫，涵蓋單字、詞組、後處理的 `羣 孃 裏 纔 樑習`）。

### 5.4 桌面檔案存取

- 選 txt 與封面：`file_picker`，取得路徑後直接讀。
- 輸出：使用者選資料夾，直接寫入；完成後 `url_launcher` 開資料夾。
- `FileAccess` 介面：`pickTxt()`、`pickCover()`、`pickOutputDirectory()`、`saveEpubs(List<EpubFile>)`（回傳結果給 UI）。
- **風險**：`file_picker` 的 API 與行為隨版本變動，以階段 3 的 PoC 確認。
- **macOS 沙盒**：macOS 應用程式預設啟用 App Sandbox。`macos/Runner/DebugProfile.entitlements` 與 `Release.entitlements` 都要加入 `com.apple.security.files.user-selected.read-write`，否則選檔與寫入資料夾會失敗。macOS 部署目標依第 1 節「最低系統版本」設定。

### 5.5 其他風險

| # | 風險 | 對策 |
|---|---|---|
| 1 | 大檔案（數十 MB）造成記憶體與卡頓 | 全程在 isolate；跳脫與簡轉繁各做一趟；先以 10 MB、50 MB 測試。選檔後若 txt 超過 20 MB（20 × 1024 × 1024 bytes），`ConvertController` 顯示確認對話框「檔案較大，轉換可能需要較長時間。是否繼續？」，使用者選擇繼續才開始轉換，選擇取消則維持原狀態。門檻定義為常數，方便調整。需有 widget 測試 |
| 2 | Java 與 Dart 正則差異（possessive quantifier、`(?i)` 內嵌旗標、命名群組語法、`\p{}` 需 `unicode: true`） | `RegExp` 建立失敗時顯示「正則表達式格式錯誤」；README 列出差異 |
| 3 | 全字串比對語意：Java `String.matches` 要求整行符合 | Dart 用 `match != null && match.start == 0 && match.end == line.length` 判斷，不可只用 `hasMatch` |
| 4 | 字元計數：Java `length()` 與 Dart `length` 都是 UTF-16 code unit | 字數模式不要改用 `runes`，才能與 Java 切點一致 |

## 6. 已同意的套件

以下套件已獲使用者同意。

| 用途 | 套件 |
|---|---|
| 狀態管理 | `flutter_riverpod`（`ConvertController` 使用 `Notifier`，`FileAccess` 以 provider 注入並在測試中覆寫） |
| 檔案選擇與儲存 | `file_picker` |
| Zip | `archive` |
| Big5 / GBK | `enough_convert` |
| 開啟資料夾 | `url_launcher` |
| 記錄 | `logging` |
| 測試與 lint | `flutter_test`、`integration_test`（兩者為 Flutter SDK 內建）、`mocktail`、`flutter_lints` |

版本與 API 注意事項（2026-11-19 依 pub.dev 最新版查證，Flutter 3.50.0 / Dart 3.14.0）：

- `file_picker` 13.x：單檔用 `pickFile()`，多檔用 `pickFiles()`，兩者回傳 `PlatformFile`，內容以 `readAsBytes()` 讀取（`withData` 已移除）。`saveFile()` 的 `fileName` 與 `bytes` 必填，不要傳已棄用的 `type`、`allowedExtensions`。`getDirectoryPath()` 已棄用，改用 `pickDirectory()`。
- `flutter_riverpod` 3.x：`StateNotifier`、`StateProvider` 已移到 `legacy.dart`，本專案不使用。Provider 以 `==` 判斷是否通知，`ConvertState` 需實作 `==` 與 `hashCode`，測試才能直接比對狀態。
- `archive` 4.6 以上：解碼器預設不再對毀損資料丟例外。階段 2 與測試驗證 zip 時，解碼要傳 `verify: true`。
- `enough_convert` 1.7.x：使用 `Big5Codec` / `GbkCodec`（或常數 `big5`、`gbk`），從 `package:enough_convert/enough_convert.dart` 匯入。

## 7. 單元測試邊界（AI）

0 章、1 章、100 章、101 章、空檔案、只有標題無內文、標題含 `& < >`、封面副檔名 png / jpg。

## 8. 完成定義

- AI：階段 5 的每個測試項目都在三次內通過（見階段 5），其中在 Mac 上，macOS 建置與整合測試通過；在 Windows 上，`flutter build windows` 與整合測試通過；兩台機器上 `flutter analyze` 與 `flutter test` 皆通過，EPUBCheck 結果已記錄。
- 使用者（M1、M2）：人工比對與閱讀器目視確認。
- 功能與 Java 版對等，所有差異記錄於 README。

---

## 附錄 A：Java 版行為規格

### A.1 畫面欄位與預設值

| 欄位 | 預設 | 說明 |
|---|---|---|
| txt 檔案 | 空 | 必填，錯誤訊息「請選擇txt檔案」 |
| 輸出路徑 | 空 | 必填，錯誤訊息「請選擇輸出路徑」 |
| 封面 | 空 | 選填 |
| 作者 | 空 | 選填，空白時 OPF 不輸出 creator |
| 出版社 | 空 | 選填，空白時 OPF 不輸出 publisher |
| 章節判斷 | 正則 | 切到「正則」時輸入框設為 `^第[0-9]{1,4}章 .*$`；切到「字數」時設為 `5000`，且只能輸入數字 |
| 排版 | 橫排 | 橫排 / 直排 |
| 簡轉繁 | 不勾選 | |
| 按鈕 | 「開始轉換」 | 轉換中禁用並顯示「轉換中...」 |
| 結果 | 成功：「轉換成功」；失敗：「EPUB 轉換失敗！」 | |

### A.2 ConvertInfo 欄位

`txtPath`、`outputPath`、`coverPath`、`author`、`publisher`、`chapterFinderType`（REGEX / WORD_COUNT）、`chapterFinder`（正則字串或字數字串）、`style`、`isConvertSimplified`。

### A.3 讀取 txt

- **正則模式**：解碼 → 逐行 → 每行先做特殊符號跳脫 → 若勾選簡轉繁則每行轉換。
- **字數模式**：解碼 → 整份內容做特殊符號跳脫 → 若勾選簡轉繁則整份轉換。
- 跳脫與簡轉繁的順序：**先跳脫，後簡轉繁**。章節正則是對**已跳脫、已轉換**的文字比對（例如標題含 `&` 時，比對的是 `&amp;`）。

### A.4 章節切分

**正則（`RegexChapterFinder`）**
1. 找出標題行索引：行不是空白，且 `trim()` 後整行符合正則。
2. 標題 = 該行原文（未 trim）。
3. 內文 = 此標題後一行到下一個標題（或檔尾）之前的所有行，過濾空白行，並移除每行中所有全形空白 `　`（U+3000）。
4. 第一個標題之前的行丟棄。找不到任何標題時章節為空，結果為 0 本書（無輸出，也無錯誤）。

**字數（`WordCountChapterFinder`）**
1. 章數 = `ceil(總長度 / 字數)`。
2. 第 i 章（從 1 起）標題為 `第{i}章`，內容為 `[(i-1)*字數, i*字數)` 的子字串（末章到結尾）。
3. 內容以「系統換行字元」切成行（Java 用 `System.lineSeparator()`，Windows 為 `\r\n`）。不過濾空白行、不移除全形空白。

**檔名**：`p-` + 章節序號補零到 4 位 + `.xhtml`，例如 `p-0001.xhtml`。序號在**整份文件**內連續，**不會**因為分冊而重新編號（`BookCreator` 依章節清單切割，章節檔名在切分時已決定）。

### A.5 分冊與書名

- 章節清單每 100 章切一本。
- 書名 = txt 檔名（去掉最後一個副檔名）+ `-` + 冊序號（從 1，補零到 2 位），例如 `三體-01`。
- 輸出檔名 = 書名 + `.epub`。輸出到使用者指定資料夾，同名覆蓋。

### A.6 樣式（DisplayStyle）

| 屬性 | 橫排 | 直排 |
|---|---|---|
| `typesettingClass` | `hltr` | `vrtl` |
| `contentH2Class` | `gfont align-center m-top-3em` | `gfont p-top-2em color-dimgray` |
| `contentFirstParagraphClass` | `m-top-2em indent-2em` | `m-right-5em` |
| `contentParagraphClass` | ` class="indent-2em"`（含前導空白與 `class=` 字樣） | 空字串 |
| `tocParagraphClass` | 空字串 | `m-top-2em` |
| `mainStyleFolder` | `horizontal` | `vertical` |

### A.7 內文 xhtml（每章一個）

文件結構（`{}` 為替換值，換行以 `\n` 表示）：

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
<html
xmlns="http://www.w3.org/1999/xhtml"
xmlns:epub="http://www.idpf.org/2007/ops"
xml:lang="zh-TW" lang="zh-TW"
class="{typesettingClass}"
>
<head>
<meta charset="UTF-8"/>
<title>{書名}</title>
<link rel="stylesheet" type="text/css" href="../style/book-style.css"/>
</head>
<body class="p-text">
<div class="main">
<h2 class="{contentH2Class}">{章節標題}</h2>
```

接著每個段落一行：
- 第 1 段：`<p class="{contentFirstParagraphClass}">{內文}</p>`
- 其餘：`<p{contentParagraphClass}>{內文}</p>`（橫排會變成 `<p class="indent-2em">`，直排是 `<p>`）

結尾：`</div>\n</body>\n</html>`。

### A.8 封面 xhtml（僅有封面時，`p-cover.xhtml`）

`<html>` class 固定為 `hltr`。`<head>` 含 `<meta charset="UTF-8"/>`、`<meta name="viewport" content="width=1444,height=2048" />`、內嵌 `<style type="text/css">html, body { margin: 0; padding: 0; width: 100%; height: 100%;}</style>`、`<title>{書名}</title>`。
`<body epub:type="cover" class="p-cover">` 內含：

```
<svg xmlns="http://www.w3.org/2000/svg" version="1.1"
xmlns:xlink="http://www.w3.org/1999/xlink"
width="100%" height="100%" viewBox="0 0 1444 2048">
<image width="1444" height="2048" xlink:href="../image/{封面檔名}"/>
</svg>
```

封面圖片複製到 `item/image/{封面檔名}`。

### A.9 目錄 xhtml（`p-toc.xhtml`）

結構同內文 xhtml 的 head（含 `class="{typesettingClass}"` 與 `book-style.css`），`<body class="p-toc">`，`<div class="main"><h1>目錄</h1>`，之後每章一行：
`<p class="{tocParagraphClass}"><a href="{章節xhtml檔名}">{章節標題}</a></p>`，結尾關閉 `</div></body></html>`。

### A.10 導覽文件（`item/navigation-documents.xhtml`）

`<html>` 無 class。`<body><nav epub:type="toc" id="toc"><h1>目錄</h1><ol>`，依序：
- 有封面時：`<li><a href="xhtml/p-cover.xhtml">封面</a></li>`
- `<li><a href="xhtml/p-toc.xhtml">目錄</a></li>`
- 每章：`<li><a href="xhtml/{章節xhtml檔名}">{章節標題}</a></li>`

結尾 `</ol></nav></body></html>`。

### A.11 OPF（`item/standard.opf`）

`<package>` 屬性：`xmlns="http://www.idpf.org/2007/opf"`、`version="3.0"`、`xml:lang="zh-TW"`、`unique-identifier="unique-id"`、`prefix="ibooks: http://vocabulary.itunes.apple.com/rdf/ibooks/vocabulary-extensions-1.0/ rendition: http://www.idpf.org/vocab/rendition/#"`。

`<metadata xmlns:dc="http://purl.org/dc/elements/1.1/">` 依序：
1. `<dc:title id="title">{書名}</dc:title>`
2. 作者非空時：`<dc:creator id="creator01">{作者}</dc:creator>`、`<meta refines="#creator01" property="role" scheme="marc:relators">aut</meta>`、`<meta refines="#creator01" property="display-seq">1</meta>`
3. 出版社非空時：`<dc:publisher id="publisher">{出版社}</dc:publisher>`
4. `<dc:language>zh-TW</dc:language>`
5. `<dc:identifier id="unique-id">urn:uuid:{隨機 UUID}</dc:identifier>`
6. `<meta property="dcterms:modified">{yyyy-MM-dd'T'HH:mm:ss'Z'}</meta>`（Java 用本地時間加上字面 `Z`，見附錄 B）
7. `<meta property="ibooks:specified-fonts">true</meta>`

`<manifest>` 依序：
1. `<item media-type="application/xhtml+xml" id="toc" href="navigation-documents.xhtml" properties="nav"/>`
2. CSS 五項，id / href：`book-style` → `style/book-style.css`，`style-reset`、`style-standard`、`style-advance`、`style-check` → `style/{id}.css`，media-type 皆 `text/css`
3. 有封面時：`<item media-type="image/jpeg" id="cover" href="image/{封面檔名}" properties="cover-image"/>` 與 `<item media-type="application/xhtml+xml" id="p-cover" href="xhtml/p-cover.xhtml" properties="svg"/>`
4. `<item media-type="application/xhtml+xml" id="p-toc" href="xhtml/p-toc.xhtml"/>`
5. 每章：`<item media-type="application/xhtml+xml" id="{xhtml 檔名去副檔名}" href="xhtml/{xhtml 檔名}"/>`

`<spine page-progression-direction="rtl">` 依序：
1. 有封面時：`<itemref linear="yes" idref="p-cover" properties="rendition:layout-pre-paginated rendition:spread-none rendition:page-spread-center"/>`
2. `<itemref linear="yes" idref="p-toc" properties="page-spread-left"/>`
3. 每章：`<itemref linear="yes" idref="{id}" properties="page-spread-left"/>`

### A.12 container.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<container version="1.0" xmlns="urn:oasis:names:tc:opendocument:xmlns:container">
  <rootfiles>
    <rootfile full-path="item/standard.opf" media-type="application/oebps-package+xml"/>
  </rootfiles>
</container>
```

（Java 由 JAXB 產生，屬性順序與縮排可能不同，內容語意相同即可，測試時以 XML 語意比對。）

### A.13 CSS 複製

`style-reset.css`、`style-standard.css`、`style-advance.css`、`style-check.css` 原樣放入 `item/style/`。`book-style.css` 依排版取自 `horizontal/` 或 `vertical/` 資料夾，輸出檔名一律 `book-style.css`。

---

## 附錄 B：原程式問題與處理決定

下列項目已由使用者決定處理方式。Dart 版直接採用修正後的行為（#1–#5、#7–#9 全部修正；#6、#10–#12 為設計差異）。與附錄 A 衝突時，**以本表為準**。

| # | 問題 | 位置 | 決定與做法 |
|---|---|---|---|
| 1 | `dc:title`、作者、出版社、封面檔名未做 XML 跳脫，含 `&`、`<` 會產生無效 XML | `OpfService`、各 xhtml 的 `<title>` | **修**。這些值寫入 XML 前以 `SpecialSymbolReplacer` 同一組規則跳脫。注意章節標題與內文在讀取階段已跳脫，**不可重複跳脫**（會變成 `&amp;amp;`）。書名來自檔名，需跳脫；作者、出版社來自 UI 輸入，需跳脫；封面檔名用在 `href` 屬性，需跳脫 `&`、`"`，並建議對 URL 保留字做百分比編碼 |
| 2 | 封面 `media-type` 固定 `image/jpeg`，PNG 封面會不符 | `OpfService.appendCoverImageItem` | **修**。依副檔名（不分大小寫）決定：`.jpg` / `.jpeg` → `image/jpeg`，`.png` → `image/png`，`.gif` → `image/gif`，`.webp` → `image/webp`，`.svg` → `image/svg+xml`。其他副檔名在選擇封面時就提示「不支援的圖片格式」並拒絕 |
| 3 | `page-progression-direction="rtl"` 對橫排書不合理（原碼有 todo） | `OpfService` | **修**。橫排 `ltr`，直排 `rtl`。另外 `itemref` 的 `page-spread-left` 屬性維持原樣，不隨排版變動（維持與 Java 版一致，避免擴大範圍） |
| 4 | `dcterms:modified` 用本地時間卻加字面 `Z`（應為 UTC） | `DateUtil` | **修**。`DateTime.now().toUtc()`，格式 `yyyy-MM-ddTHH:mm:ssZ`（秒以下捨去，不含毫秒）。時間來源要可注入，方便測試 |
| 5 | 字數模式輸入 0 會除以零；非數字會丟例外且無友善提示 | `WordCountChapterFinder` | **修**。`ConvertController` 驗證字數須為正整數（大於 0），否則顯示「字數必須為大於 0 的整數」；欄位同時用 `FilteringTextInputFormatter.digitsOnly` 限制輸入。`WordCountChapterFinder` 本身遇到非法值也要丟明確的例外，不依賴 UI |
| 6 | 字數模式用 `System.lineSeparator()` 切行，非 Windows 平台或換行字元不同時，整章變一行 | `WordCountChapterFinder` | **修**。用 `LineSplitter`（同時處理 `\r\n`、`\n`、`\r`） |
| 7 | Big5 解碼幾乎不失敗，GBK 簡體檔常被誤判為 Big5 | `TxtCharset` | **修**。加入編碼偵測，流程見 5.1 |
| 8 | 不移除 UTF-8 BOM，BOM 會出現在第一行，可能讓第一個標題無法比對 | `RegexTxtReader` | **修**。解碼後移除開頭 U+FEFF（見 5.1） |
| 9 | 找不到任何章節時產生 0 本書，使用者看到「轉換成功」但沒有檔案 | `BookCreator` / `EpubService` | **修**。`EpubService` 在章節為空時丟 `NoChapterFoundException`；UI 顯示「找不到任何章節，請檢查章節判斷設定」，不顯示成功訊息，也不產生任何檔案 |
| 10 | 封面路徑為空字串時，`Paths.get("")` 非 `null`，靠 `isRegularFile` 擋掉 | `Book` | Dart 版以 `String?` / `Uint8List?` 表示，明確判斷 |
| 11 | 暫存資料夾以書名命名，同名並行轉換會互相覆蓋 | `TempDirectory` | Dart 版記憶體打包，自然消失 |
| 12 | 標題用於 `<title>` / `<a>` 時是否已跳脫取決於讀取順序 | 讀取階段 | 保留現狀：章節標題與內文來自已跳脫的文字，所以安全（與 #1 的「不可重複跳脫」呼應） |
