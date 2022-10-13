import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import locale from 'element-plus/lib/locale/lang/zh-cn';
import { Search, ArrowDown, QuestionFilled, User, Unlock, View, Hide, UploadFilled, Close, Back } from '@element-plus/icons-vue';
export default (app) => {
  app.component(Search.name, Search);
  app.component(ArrowDown.name, ArrowDown);
  app.component(QuestionFilled.name, QuestionFilled);
  app.component(User.name, User);
  app.component(Unlock.name, Unlock);
  app.component(View.name, View);
  app.component(Hide.name, Hide);
  app.component(UploadFilled.name, UploadFilled);
  app.component(Close.name, Close);
  app.component(Back.name, Back);

  app.use(ElementPlus, { locale });
};
