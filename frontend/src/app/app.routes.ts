import { Routes } from '@angular/router';
import { OrderListComponent } from './pages/order-list/order-list.component';
import { OrderFormComponent } from './pages/order-form/order-form.component';

export const routes: Routes = [
  { path: '', redirectTo: 'orders', pathMatch: 'full' },  // redirect root to /orders
  { path: 'orders', component: OrderListComponent },
  { path: 'orders/new', component: OrderFormComponent }
];
