import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MapComponent } from './components/map/map.component';
import { MapService } from './services/map.service';
import { SunExpositionComponent } from './components/sun-exposition/sun-exposition.component';
import {TableModule} from 'primeng/table';
import {ButtonModule} from 'primeng/button';
import {CheckboxModule} from 'primeng/checkbox';
import {ToastModule} from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MessagePopupComponent } from './components/message-popup/message-popup.component';
import { CustomMessagesService } from './services/custom-messages.service';



@NgModule({
  declarations: [
    AppComponent,
    MapComponent,
    SunExpositionComponent,
    MessagePopupComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    TableModule,
    ButtonModule,
    CheckboxModule,
    ToastModule,
    BrowserAnimationsModule
  ],
  providers: [MapService, MessageService, CustomMessagesService],
  bootstrap: [AppComponent]
})
export class AppModule { }
